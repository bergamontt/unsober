package ua.unsober.backend.common.utils.studyplan;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ua.unsober.backend.common.enums.*;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;
import ua.unsober.backend.common.utils.studyplan.views.*;
import ua.unsober.backend.feature.appstate.AppStateService;
import ua.unsober.backend.feature.department.Department;
import ua.unsober.backend.feature.faculty.Faculty;
import ua.unsober.backend.feature.speciality.Speciality;
import ua.unsober.backend.feature.student.Student;
import ua.unsober.backend.feature.student.StudentRepository;
import ua.unsober.backend.feature.studentenrollment.StudentEnrollment;
import ua.unsober.backend.feature.studentenrollment.StudentEnrollmentRepository;
import ua.unsober.backend.feature.subject.Subject;
import ua.unsober.backend.feature.subjectrecommendation.SubjectRecommendation;
import ua.unsober.backend.feature.subjectrecommendation.SubjectRecommendationRepository;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StudyPlanServiceImpl implements StudyPlanService {

    private final TemplateEngine templateEngine;
    private final StudentRepository studentRepository;
    private final StudentEnrollmentRepository enrollmentRepository;
    private final SubjectRecommendationRepository recommendationRepository;
    private final AppStateService appStateService;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    @Value("${study-plan.pdf-dir}")
    private String dirPath;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    @Transactional(readOnly = true)
    public void generateStudyPlan(UUID id) throws IOException {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> notFound.get("error.student.notfound", id));

        Integer currentYear = appStateService.getAppState().getCurrentYear();
        List<StudentEnrollment> allEnrollments = enrollmentRepository.findAllByStudentId(id);

        List<StudentEnrollment> filtered = allEnrollments.stream()
                .filter(this::validateEnrollment)
                .toList();

        List<StudentEnrollment> currentYearEnrollments = filtered.stream()
                .filter(e -> Objects.equals(e.getEnrollmentYear(), currentYear))
                .toList();

        List<StudentEnrollment> previousYearsEnrollments = filtered.stream()
                .filter(e -> e.getEnrollmentYear() != null && e.getEnrollmentYear() < currentYear)
                .toList();

        BigDecimal completedCredits = computeTotalCredits(previousYearsEnrollments);
        String academicYear = computeAcademicYear(currentYear);
        String educationLevel = buildEducationLevel(currentYearEnrollments);
        StudyPlanStudentView studentView = buildStudentView(student, completedCredits, educationLevel);
        StudyPlanSectionsView sections = buildSections(student, currentYearEnrollments);
        StudyPlanYearTotalsView yearTotals = buildYearTotals(sections);

        Context ctx = new Context(Locale.forLanguageTag("uk"));
        ctx.setVariable("student", studentView);
        ctx.setVariable("academicYear", academicYear);
        ctx.setVariable("currentDate", LocalDate.now().format(DATE_FORMAT));
        ctx.setVariable("currentYear", LocalDate.now().getYear());

        ctx.setVariable("mandatorySection", sections.getMandatorySection());
        ctx.setVariable("profOrientedSection", sections.getProfOrientedSection());
        ctx.setVariable("freeChoiceSection", sections.getFreeChoiceSection());
        ctx.setVariable("yearTotals", yearTotals);

        String html = templateEngine.process("studyplan/study-plan", ctx);
        byte[] pdfBytes = renderPdfFromHtml(html);

        savePdfToFile(id, pdfBytes);
    }

    private boolean validateEnrollment(StudentEnrollment e) {
        return (e.getStatus() == EnrollmentStatus.ENROLLED
                || e.getStatus() == EnrollmentStatus.FORCE_ENROLLED)
                && e.getCourse() != null
                && e.getCourse().getSubject() != null;
    }

    private BigDecimal computeTotalCredits(List<StudentEnrollment> enrollments) {
        return enrollments.stream()
                .map(e -> e.getCourse().getSubject().getCredits())
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private String computeAcademicYear(Integer year) {
        return year + "–" + (year + 1) + " н.р.";
    }

    private StudyPlanStudentView buildStudentView(Student student, BigDecimal totalCredits, String degreeLevel) {
        String specialityName = Optional.ofNullable(student.getSpeciality())
                .map(Speciality::getName)
                .orElse(null);

        String facultyName = Optional.ofNullable(student.getSpeciality())
                .map(Speciality::getDepartment)
                .map(Department::getFaculty)
                .map(Faculty::getName)
                .orElse(null);

        return StudyPlanStudentView.builder()
                .fullName(buildFullName(student))
                .specialityName(specialityName)
                .studyYear(student.getStudyYear())
                .educationLevel(degreeLevel)
                .faculty(facultyName)
                .totalCredits(totalCredits)
                .build();
    }

    private String buildFullName(Student student) {
        StringBuilder sb = new StringBuilder();
        if (student.getUser().getLastName() != null) {
            sb.append(student.getUser().getLastName()).append(" ");
        }
        if (student.getUser().getFirstName() != null) {
            sb.append(student.getUser().getFirstName()).append(" ");
        }
        if (student.getUser().getPatronymic() != null) {
            sb.append(student.getUser().getPatronymic());
        }
        return sb.toString().trim();
    }

    private String buildEducationLevel(List<StudentEnrollment> enrollments) {
        return enrollments.stream()
                .map(e -> e.getCourse().getSubject().getEducationLevel())
                .filter(Objects::nonNull)
                .findFirst()
                .map(level -> switch (level) {
                    case BATCHELOR -> "Бакалавр";
                    case MASTER -> "Магістр";
                })
                .orElse("—");
    }

    private StudyPlanCategory resolveCategory(Student student, Subject subject) {
        if (student.getSpeciality() == null) {
            return StudyPlanCategory.FREE_CHOICE;
        }

        Recommendation rec = recommendationRepository
                .findBySubjectIdAndSpecialityId(subject.getId(), student.getSpeciality().getId())
                .map(SubjectRecommendation::getRecommendation)
                .orElse(null);

        if (rec == null) return StudyPlanCategory.FREE_CHOICE;

        return switch (rec) {
            case MANDATORY -> StudyPlanCategory.MANDATORY;
            case PROF_ORIENTED -> StudyPlanCategory.PROF_ORIENTED;
        };
    }

    private BigDecimal computeTotalHours(Subject subject) {
        return subject.getCredits().multiply(BigDecimal.valueOf(30));
    }

    private StudyPlanCourseRowView buildCourseRow(Subject subject, int index) {
        StudyPlanTermLoadView load = StudyPlanTermLoadView.builder()
                .hoursPerWeek(subject.getHoursPerWeek())
                .credits(subject.getCredits())
                .build();

        StudyPlanCourseRowView.StudyPlanCourseRowViewBuilder builder = StudyPlanCourseRowView.builder()
                .index(index)
                .subjectName(subject.getName())
                .totalHours(computeTotalHours(subject))
                .comment("");

        switch (subject.getTerm()) {
            case AUTUMN -> {
                builder.autumn(load);
                builder.spring(StudyPlanTermLoadView.empty());
                builder.summer(StudyPlanTermLoadView.empty());
            }
            case SPRING -> {
                builder.autumn(StudyPlanTermLoadView.empty());
                builder.spring(load);
                builder.summer(StudyPlanTermLoadView.empty());
            }
            case SUMMER -> {
                builder.autumn(StudyPlanTermLoadView.empty());
                builder.spring(StudyPlanTermLoadView.empty());
                builder.summer(load);
            }
            default -> {
                builder.autumn(StudyPlanTermLoadView.empty());
                builder.spring(StudyPlanTermLoadView.empty());
                builder.summer(StudyPlanTermLoadView.empty());
            }
        }

        return builder.build();
    }

    private StudyPlanSectionView buildSection(List<StudyPlanCourseRowView> rows) {
        int autumnHours = 0;
        int springHours = 0;
        int summerHours = 0;

        BigDecimal autumnCredits = BigDecimal.ZERO;
        BigDecimal springCredits = BigDecimal.ZERO;
        BigDecimal summerCredits = BigDecimal.ZERO;

        for (StudyPlanCourseRowView row : rows) {
            if (row.getAutumn() != null) {
                autumnHours += row.getAutumn().getHoursPerWeek();
                autumnCredits = autumnCredits.add(row.getAutumn().getCredits());
            }
            if (row.getSpring() != null) {
                springHours += row.getSpring().getHoursPerWeek();
                springCredits = springCredits.add(row.getSpring().getCredits());
            }
            if (row.getSummer() != null) {
                summerHours += row.getSummer().getHoursPerWeek();
                summerCredits = summerCredits.add(row.getSummer().getCredits());
            }
        }

        return StudyPlanSectionView.builder()
                .subjects(rows)
                .autumnTotal(StudyPlanTermLoadView.of(autumnHours, autumnCredits))
                .springTotal(StudyPlanTermLoadView.of(springHours, springCredits))
                .summerTotal(StudyPlanTermLoadView.of(summerHours, summerCredits))
                .build();
    }

    private StudyPlanSectionsView buildSections(
            Student student,
            List<StudentEnrollment> enrollments
    ) {
        List<StudyPlanCourseRowView> mandatory = new ArrayList<>();
        List<StudyPlanCourseRowView> profOriented = new ArrayList<>();
        List<StudyPlanCourseRowView> freeChoice = new ArrayList<>();

        int mandatoryIdx = 1;
        int profIdx = 1;
        int freeIdx = 1;

        for (StudentEnrollment e : enrollments) {
            Subject subject = e.getCourse().getSubject();
            StudyPlanCategory category = resolveCategory(student, subject);

            switch (category) {
                case MANDATORY -> {
                    mandatory.add(buildCourseRow(subject, mandatoryIdx++));
                }
                case PROF_ORIENTED -> {
                    profOriented.add(buildCourseRow(subject, profIdx++));
                }
                case FREE_CHOICE -> {
                    freeChoice.add(buildCourseRow(subject, freeIdx++));
                }
            }
        }

        StudyPlanSectionView mandatorySection = buildSection(mandatory);
        StudyPlanSectionView profOrientedSection = buildSection(profOriented);
        StudyPlanSectionView freeChoiceSection = buildSection(freeChoice);

        return StudyPlanSectionsView.builder()
                .mandatorySection(mandatorySection)
                .profOrientedSection(profOrientedSection)
                .freeChoiceSection(freeChoiceSection)
                .build();
    }

    private StudyPlanYearTotalsView buildYearTotals(StudyPlanSectionsView sections) {
        StudyPlanSectionView mandatory = sections.getMandatorySection();
        StudyPlanSectionView prof = sections.getProfOrientedSection();
        StudyPlanSectionView free = sections.getFreeChoiceSection();

        StudyPlanTermLoadView autumn = StudyPlanTermLoadView.of(
                mandatory.getAutumnTotal().getHoursPerWeek()
                        + prof.getAutumnTotal().getHoursPerWeek()
                        + free.getAutumnTotal().getHoursPerWeek(),
                mandatory.getAutumnTotal().getCredits()
                        .add(prof.getAutumnTotal().getCredits())
                        .add(free.getAutumnTotal().getCredits())
        );

        StudyPlanTermLoadView spring = StudyPlanTermLoadView.of(
                mandatory.getSpringTotal().getHoursPerWeek()
                        + prof.getSpringTotal().getHoursPerWeek()
                        + free.getSpringTotal().getHoursPerWeek(),
                mandatory.getSpringTotal().getCredits()
                        .add(prof.getSpringTotal().getCredits())
                        .add(free.getSpringTotal().getCredits())
        );

        StudyPlanTermLoadView summer = StudyPlanTermLoadView.of(
                mandatory.getSummerTotal().getHoursPerWeek()
                        + prof.getSummerTotal().getHoursPerWeek()
                        + free.getSummerTotal().getHoursPerWeek(),
                mandatory.getSummerTotal().getCredits()
                        .add(prof.getSummerTotal().getCredits())
                        .add(free.getSummerTotal().getCredits())
        );

        BigDecimal totalCredits = autumn.getCredits()
                .add(spring.getCredits())
                .add(summer.getCredits());

        return StudyPlanYearTotalsView.builder()
                .autumn(autumn)
                .spring(spring)
                .summer(summer)
                .credits(totalCredits)
                .build();
    }

    private byte[] renderPdfFromHtml(String html) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            String baseUrl = Objects.requireNonNull(Thread.currentThread()
                            .getContextClassLoader()
                            .getResource(""))
                    .toExternalForm();

            PdfRendererBuilder builder = getPdfRendererBuilder(html, baseUrl);
            builder.toStream(out);
            builder.run();

            return out.toByteArray();
        }
    }

    private static PdfRendererBuilder getPdfRendererBuilder(String html, String baseUrl) {
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.useFastMode();
        builder.withHtmlContent(html, baseUrl);
        builder.useFont(() -> Thread.currentThread()
                        .getContextClassLoader()
                        .getResourceAsStream("fonts/DejaVuSans.ttf"), "DejaVu");
        return builder;
    }

    private void savePdfToFile(UUID id, byte[] pdfBytes) throws IOException {
        Path dir = Path.of(dirPath);
        Files.createDirectories(dir);

        Path file = dir.resolve("study-plan-" + id.toString() + ".pdf");
        try (FileOutputStream fos = new FileOutputStream(file.toFile())) {
            fos.write(pdfBytes);
        }
    }

}
