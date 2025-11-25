package ua.unsober.backend.feature.studentenrollment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.exceptions.LocalizedCourseFullExceptionFactory;
import ua.unsober.backend.common.exceptions.LocalizedEnrollmentActionNotAllowedExceptionFactory;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;
import ua.unsober.backend.common.exceptions.LocalizedGroupFullExceptionFactory;
import ua.unsober.backend.feature.appstate.AppStateService;
import ua.unsober.backend.feature.course.Course;
import ua.unsober.backend.feature.coursegroup.CourseGroup;
import ua.unsober.backend.feature.coursegroup.CourseGroupRepository;
import ua.unsober.backend.feature.course.CourseRepository;
import ua.unsober.backend.feature.student.StudentResponseDto;
import ua.unsober.backend.feature.student.StudentService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentEnrollmentServiceImpl implements StudentEnrollmentService {

    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final CourseRepository courseRepository;
    private final CourseGroupRepository courseGroupRepository;
    private final StudentService studentService;
    private final AppStateService appStateService;
    private final StudentEnrollmentRequestMapper requestMapper;
    private final StudentEnrollmentResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;
    private final LocalizedCourseFullExceptionFactory courseFull;
    private final LocalizedGroupFullExceptionFactory groupFull;
    private final LocalizedEnrollmentActionNotAllowedExceptionFactory notAllowed;

    private static final Marker ENROLLMENT_ACTION = MarkerFactory.getMarker("ENROLLMENT_ACTION");

    private Course validateCourse(UUID courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> notFound.get("error.course.notfound", courseId));

        if (course.getMaxStudents() != null && course.getNumEnrolled() >= course.getMaxStudents()) {
            log.warn(ENROLLMENT_ACTION, "Course with id={} is full", courseId);
            throw courseFull.get();
        }

        return course;
    }

    private CourseGroup validateGroup(UUID groupId) {
        CourseGroup courseGroup = courseGroupRepository.findById(groupId)
                .orElseThrow(() -> notFound.get("error.course-group.notfound", groupId));

        if (courseGroup.getNumEnrolled() >= courseGroup.getMaxStudents()) {
            log.warn(ENROLLMENT_ACTION, "Course group with id={} is full", groupId);
            throw groupFull.get();
        }

        return courseGroup;
    }

    @Override
    public StudentEnrollmentResponseDto create(StudentEnrollmentRequestDto dto) {
        Course course = validateCourse(dto.getCourseId());
        CourseGroup courseGroup = dto.getGroupId() != null ? validateGroup(dto.getGroupId()) : null;

        StudentEnrollment enrollment = requestMapper.toEntity(dto);
        enrollment.setCourse(course);
        enrollment.setGroup(courseGroup);

        course.setNumEnrolled(course.getNumEnrolled() + 1);
        if (courseGroup != null) courseGroup.setNumEnrolled(courseGroup.getNumEnrolled() + 1);

        StudentEnrollment saved = studentEnrollmentRepository.save(enrollment);
        return responseMapper.toDto(saved);
    }

    @Override
    public List<StudentEnrollmentResponseDto> getAll() {
        return studentEnrollmentRepository.findAll()
                .stream()
                .map(responseMapper::toDto)
                .toList();
    }

    @Override
    public StudentEnrollmentResponseDto getById(UUID id) {
        StudentEnrollment enrollment = studentEnrollmentRepository.findById(id)
                .orElseThrow(() -> notFound.get("error.student-enrollment.notfound", id));
        return responseMapper.toDto(enrollment);
    }

    @Override
    public List<StudentEnrollmentResponseDto> getAllByStudentId(UUID studentId) {
        return studentEnrollmentRepository.findAllByStudentId(studentId)
                .stream()
                .map(responseMapper::toDto)
                .toList();
    }

    @Override
    public List<StudentEnrollmentResponseDto> getAllByCourseId(UUID courseId) {
        return studentEnrollmentRepository.findAllByCourseId(courseId)
                .stream()
                .map(responseMapper::toDto)
                .toList();
    }

    @Override
    public StudentEnrollmentResponseDto update(UUID id, StudentEnrollmentRequestDto dto) {
        StudentEnrollment enrollment = studentEnrollmentRepository.findById(id)
                .orElseThrow(() -> notFound.get("error.student-enrollment.notfound", id));

        if (dto.getCourseId() != null) {
            Course oldCourse = enrollment.getCourse();
            Course newCourse = validateCourse(dto.getCourseId());
            oldCourse.setNumEnrolled(oldCourse.getNumEnrolled() - 1);
            newCourse.setNumEnrolled(newCourse.getNumEnrolled() + 1);
            enrollment.setCourse(newCourse);
        }

        if (dto.getGroupId() != null) {
            CourseGroup oldGroup = enrollment.getGroup();
            CourseGroup newGroup = validateGroup(dto.getGroupId());
            if (oldGroup != null) oldGroup.setNumEnrolled(oldGroup.getNumEnrolled() - 1);
            newGroup.setNumEnrolled(newGroup.getNumEnrolled() + 1);
            enrollment.setGroup(newGroup);
        }

        StudentEnrollment newEnrollment = requestMapper.toEntity(dto);
        if (newEnrollment.getStudent() != null) enrollment.setStudent(newEnrollment.getStudent());
        if (newEnrollment.getStatus() != null) enrollment.setStatus(newEnrollment.getStatus());
        if (newEnrollment.getEnrollmentYear() != null) enrollment.setEnrollmentYear(newEnrollment.getEnrollmentYear());

        StudentEnrollment updated = studentEnrollmentRepository.save(enrollment);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        StudentEnrollment enrollment = studentEnrollmentRepository.findById(id)
                .orElseThrow(() -> notFound.get("error.student-enrollment.notfound", id));

        Course course = enrollment.getCourse();
        course.setNumEnrolled(course.getNumEnrolled() - 1);
        courseRepository.save(course);

        CourseGroup group = enrollment.getGroup();
        if (group != null) {
            group.setNumEnrolled(group.getNumEnrolled() - 1);
            courseGroupRepository.save(group);
        }

        studentEnrollmentRepository.delete(enrollment);
    }

    @Override
    public StudentEnrollmentResponseDto enrollSelf(UUID courseId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        StudentResponseDto student = studentService.getByEmail(email);
        int currYear = appStateService.getAppState().getCurrentYear();
        return create(
                StudentEnrollmentRequestDto.builder()
                        .studentId(student.getId())
                        .courseId(courseId)
                        .enrollmentYear(currYear)
                        .build()
        );
    }

    @Override
    public StudentEnrollmentResponseDto changeGroup(UUID enrollmentId, UUID groupId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        StudentResponseDto student = studentService.getByEmail(email);
        StudentEnrollmentResponseDto enrollment = getById(enrollmentId);
        if(!enrollment.getStudent().getId().equals(student.getId())){
            throw notAllowed.get();
        }
        return update(enrollmentId,
                StudentEnrollmentRequestDto.builder()
                        .groupId(groupId)
                        .build()
        );
    }
}
