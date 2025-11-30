package ua.unsober.backend.common.utils.parser;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.unsober.backend.common.enums.ClassType;
import ua.unsober.backend.common.enums.WeekDay;
import ua.unsober.backend.common.utils.parser.models.ClassSchedule;
import ua.unsober.backend.common.utils.parser.models.DaySchedule;
import ua.unsober.backend.common.utils.parser.models.Schedule;
import ua.unsober.backend.feature.course.Course;
import ua.unsober.backend.feature.course.CourseRepository;
import ua.unsober.backend.feature.courseclass.CourseClass;
import ua.unsober.backend.feature.courseclass.CourseClassRepository;
import ua.unsober.backend.feature.coursegroup.CourseGroup;
import ua.unsober.backend.feature.coursegroup.CourseGroupRepository;
import ua.unsober.backend.feature.subject.Subject;
import ua.unsober.backend.feature.subject.SubjectRepository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchedulePersistenceService {

    private final SubjectRepository subjectRepository;
    private final CourseRepository courseRepository;
    private final CourseGroupRepository courseGroupRepository;
    private final CourseClassRepository courseClassRepository;

    @Transactional
    public void saveSchedule(Schedule schedule, int yearOfStudy) {
        if (schedule == null || schedule.getDaySchedules() == null)
            return;
        for (DaySchedule daySchedule : schedule.getDaySchedules()) {
            if (daySchedule == null || daySchedule.getClasses() == null)
                continue;
            for (ClassSchedule classDto : daySchedule.getClasses()) {
                if (classDto == null)
                    continue;
                processClassDto(schedule, yearOfStudy, classDto);
            }
        }
    }

    private void processClassDto(Schedule schedule, int yearOfStudy, ClassSchedule classDto) {
        String subjectName = classDto.getClassName();
        Subject subject = findSubjectOrThrow(subjectName, schedule.getSpecialityName());
        Course course = findOrCreateCourse(subject, yearOfStudy);
        String groupRaw = classDto.getGroup();
        if (groupRaw == null)
            groupRaw = "";
        if (groupRaw.trim().equalsIgnoreCase("лекція")) {
            handleLecture(classDto, course);
        } else {
            handlePractice(classDto, course, groupRaw);
        }
    }

    private Subject findSubjectOrThrow(String subjectName, String specialityName) {
        List<Subject> subjects = subjectRepository.findByName(subjectName);
        if (subjects.isEmpty())
            throw new EntityNotFoundException("Subject not found: " + subjectName);
        if (subjects.size() > 1) {
            return subjectRepository.findByNameAndSpecialityName(subjectName, specialityName)
                    .orElseThrow(() -> new EntityNotFoundException("Subject not found: " + subjectName + " for " + specialityName));
        } else {
            return subjects.getFirst();
        }
    }

    private Course findOrCreateCourse(Subject subject, int yearOfStudy) {
        return courseRepository.findBySubjectIdAndCourseYear(subject.getId(), yearOfStudy)
                .orElseGet(() -> createCourseForSubject(subject, yearOfStudy));
    }

    private void handleLecture(ClassSchedule classDto, Course course) {
        List<CourseGroup> groups = courseGroupRepository.findByCourseId(course.getId());
        if (groups.isEmpty()) {
            CourseGroup g = createCourseGroup(course, 1);
            groups = Collections.singletonList(g);
        }
        for (CourseGroup g : groups) {
            CourseClass cc = buildCourseClassFromDto(classDto, g, ClassType.LECTURE);
            courseClassRepository.save(cc);
        }
    }

    private void handlePractice(ClassSchedule classDto, Course course, String groupRaw) {
        List<Integer> groupNumbers = parseGroupNumbers(groupRaw);
        for (Integer groupNumber : groupNumbers) {
            CourseGroup group = courseGroupRepository.findByCourseIdAndGroupNumber(course.getId(), groupNumber)
                    .orElseGet(() -> createCourseGroup(course, groupNumber));
            CourseClass cc = buildCourseClassFromDto(classDto, group, ClassType.PRACTICE);
            courseClassRepository.save(cc);
        }
    }

    private Course createCourseForSubject(Subject subject, int courseYear) {
        Course c = Course.builder()
                .subject(subject)
                .maxStudents(null)
                .numEnrolled(0)
                .courseYear(courseYear)
                .build();
        return courseRepository.save(c);
    }

    private CourseGroup createCourseGroup(Course course, int groupNumber) {
        CourseGroup cg = CourseGroup.builder()
                .course(course)
                .groupNumber(groupNumber)
                .maxStudents(10)
                .numEnrolled(0)
                .build();
        return courseGroupRepository.save(cg);
    }

    private CourseClass buildCourseClassFromDto(ClassSchedule dto, CourseGroup group, ClassType type) {
        String title = dto.getClassName();
        String weeksList = dto.getWeeks() == null ? "" :
                dto.getWeeks().stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(","));

        int weekdayIndex = dto.getWeekday();
        if (weekdayIndex < 0 || weekdayIndex >= WeekDay.values().length) {
            throw new IllegalArgumentException("Invalid weekday index: " + weekdayIndex);
        }
        WeekDay weekDay = WeekDay.values()[weekdayIndex];

        Integer classNumber = getClassNumber(dto.getStartTime(), dto.getEndTime());
        if (classNumber == null) {
            throw new IllegalArgumentException("Could not map startTime to class number: " + dto.getStartTime());
        }

        return CourseClass.builder()
                .group(group)
                .title(title)
                .type(type)
                .weeksList(weeksList)
                .weekDay(weekDay)
                .classNumber(classNumber)
                .location(dto.getLocation())
                .teacher(null)
                .building(null)
                .build();
    }

    private List<Integer> parseGroupNumbers(String raw) {
        String trimmed = raw == null ? "" : raw.trim();
        if (trimmed.isEmpty()) return Collections.singletonList(1);
        String[] parts = trimmed.split(",");
        List<Integer> out = new ArrayList<>();
        for (String p : parts) {
            String s = p.trim();
            if (s.isEmpty()) continue;
            try {
                out.add(Integer.parseInt(s));
            } catch (NumberFormatException ignored) {
                // ignored
            }
        }
        if (out.isEmpty()) {
            out.add(1);
        }
        return out;
    }

    private Integer getClassNumber(LocalTime startTime, LocalTime endTime) {
        if (startTime == null || endTime == null)
            return null;
        List<TimeSlot> slots = Arrays.asList(
                new TimeSlot(1, LocalTime.of(8, 30), LocalTime.of(9, 50)),
                new TimeSlot(2, LocalTime.of(10, 0), LocalTime.of(11, 20)),
                new TimeSlot(3, LocalTime.of(11, 40), LocalTime.of(13, 0)),
                new TimeSlot(4, LocalTime.of(13, 30), LocalTime.of(14, 50)),
                new TimeSlot(5, LocalTime.of(15, 0), LocalTime.of(16, 20)),
                new TimeSlot(6, LocalTime.of(16, 30), LocalTime.of(17, 50)),
                new TimeSlot(7, LocalTime.of(18, 0), LocalTime.of(19, 20))
        );

        for (TimeSlot ts : slots) {
            if (!startTime.isBefore(ts.start) && startTime.isBefore(ts.end)) {
                return ts.number;
            }
        }
        return null;
    }

    private record TimeSlot(int number, LocalTime start, LocalTime end) {
    }
}