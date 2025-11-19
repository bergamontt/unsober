package ua.unsober.backend.feature.studentEnrollment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.exceptions.LocalizedCourseFullExceptionFactory;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;
import ua.unsober.backend.common.exceptions.LocalizedGroupFullExceptionFactory;
import ua.unsober.backend.feature.course.Course;
import ua.unsober.backend.feature.courseGroup.CourseGroup;
import ua.unsober.backend.feature.courseGroup.CourseGroupRepository;
import ua.unsober.backend.feature.course.CourseRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentEnrollmentServiceImpl implements StudentEnrollmentService {

    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final CourseRepository courseRepository;
    private final CourseGroupRepository courseGroupRepository;
    private final StudentEnrollmentRequestMapper requestMapper;
    private final StudentEnrollmentResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;
    private final LocalizedCourseFullExceptionFactory courseFull;
    private final LocalizedGroupFullExceptionFactory groupFull;

    private static final Marker ENROLLMENT_ACTION = MarkerFactory.getMarker("ENROLLMENT_ACTION");

    private Course validateCourse(UUID courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> notFound.get("error.course.notfound", courseId));

        if (course.getNumEnrolled() >= course.getMaxStudents()) {
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
                .orElseThrow(() -> {
                    return notFound.get("error.student-enrollment.notfound", id);
                });
        return responseMapper.toDto(enrollment);
    }

    @Override
    public StudentEnrollmentResponseDto update(UUID id, StudentEnrollmentRequestDto dto) {
        StudentEnrollment enrollment = studentEnrollmentRepository.findById(id)
                .orElseThrow(() -> {
                    return notFound.get("error.student-enrollment.notfound", id);
                });

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
                .orElseThrow(() -> {
                    return notFound.get("error.student-enrollment.notfound", id);
                });

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
}
