package ua.unsober.backend.feature.studentenrollment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.enums.EnrollmentStatus;
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

    private static final String ENROLLMENT_NOT_FOUND = "error.student-enrollment.notfound";
    private static final String COURSE_NOT_FOUND = "error.course.notfound";
    private static final String GROUP_NOT_FOUND = "error.course-group.notfound";
    private static final Marker ENROLLMENT_ACTION = MarkerFactory.getMarker("ENROLLMENT_ACTION");

    private Course validateCourse(UUID courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> notFound.get(COURSE_NOT_FOUND, courseId));

        if (course.getMaxStudents() != null && course.getNumEnrolled() >= course.getMaxStudents()) {
            log.warn(ENROLLMENT_ACTION, "Course with id={} is full", courseId);
            throw courseFull.get();
        }

        return course;
    }

    private CourseGroup validateGroup(UUID groupId) {
        CourseGroup courseGroup = courseGroupRepository.findById(groupId)
                .orElseThrow(() -> notFound.get(GROUP_NOT_FOUND, groupId));

        if (courseGroup.getNumEnrolled() >= courseGroup.getMaxStudents()) {
            log.warn(ENROLLMENT_ACTION, "Course group with id={} is full", groupId);
            throw groupFull.get();
        }

        return courseGroup;
    }

    private void decreaseEnrollmentCounters(StudentEnrollment enrollment){
        Course course = enrollment.getCourse();
        course.setNumEnrolled(course.getNumEnrolled() - 1);
        courseRepository.save(course);

        CourseGroup group = enrollment.getGroup();
        if (group != null) {
            group.setNumEnrolled(group.getNumEnrolled() - 1);
            courseGroupRepository.save(group);
        }
    }

    @Override
    public StudentEnrollmentResponseDto create(StudentEnrollmentRequestDto dto) {
        Course course = validateCourse(dto.getCourseId());
        CourseGroup courseGroup = dto.getGroupId() != null ? validateGroup(dto.getGroupId()) : null;

        StudentEnrollment enrollment = requestMapper.toEntity(dto);
        enrollment.setStatus(EnrollmentStatus.ENROLLED);
        enrollment.setCourse(course);
        enrollment.setGroup(courseGroup);

        course.setNumEnrolled(course.getNumEnrolled() + 1);
        if (courseGroup != null)
            courseGroup.setNumEnrolled(courseGroup.getNumEnrolled() + 1);

        StudentEnrollment saved = studentEnrollmentRepository.save(enrollment);
        courseRepository.save(course);
        if(courseGroup != null)
            courseGroupRepository.save(courseGroup);
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
                .orElseThrow(() -> notFound.get(ENROLLMENT_NOT_FOUND, id));
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
    public List<StudentEnrollmentResponseDto> getAllByStudentIdAndYear(UUID studentId, Integer year) {
        return studentEnrollmentRepository.findAllByStudentIdAndEnrollmentYear(studentId, year)
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
    public List<Integer> getAllYearsByStudentId(UUID studentId) {
        return studentEnrollmentRepository.findEnrollmentYearsByStudentId(studentId);
    }

    @Override
    public boolean existsByStudentAndCourseId(UUID studentId, UUID courseId) {
        return  studentEnrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId);
    }

    @Override
    public StudentEnrollmentResponseDto update(UUID id, StudentEnrollmentRequestDto dto) {
        StudentEnrollment enrollment = studentEnrollmentRepository.findById(id)
                .orElseThrow(() -> notFound.get(ENROLLMENT_NOT_FOUND, id));

        if (dto.getCourseId() != null) {
            Course oldCourse = enrollment.getCourse();
            Course newCourse = validateCourse(dto.getCourseId());
            oldCourse.setNumEnrolled(oldCourse.getNumEnrolled() - 1);
            newCourse.setNumEnrolled(newCourse.getNumEnrolled() + 1);
            enrollment.setCourse(newCourse);
            courseRepository.save(oldCourse);
            courseRepository.save(newCourse);
        }

        if (dto.getGroupId() != null) {
            CourseGroup oldGroup = enrollment.getGroup();
            CourseGroup newGroup = validateGroup(dto.getGroupId());
            if (oldGroup != null)
                oldGroup.setNumEnrolled(oldGroup.getNumEnrolled() - 1);
            newGroup.setNumEnrolled(newGroup.getNumEnrolled() + 1);
            enrollment.setGroup(newGroup);
            if(oldGroup != null)
                courseGroupRepository.save(oldGroup);
            courseGroupRepository.save(newGroup);
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
                .orElseThrow(() -> notFound.get(ENROLLMENT_NOT_FOUND, id));
        decreaseEnrollmentCounters(enrollment);
        enrollment.setStatus(EnrollmentStatus.WITHDRAWN);
        studentEnrollmentRepository.save(enrollment);
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

    @Override
    public void withdrawFromGroup(UUID enrollmentId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        StudentResponseDto student = studentService.getByEmail(email);
        StudentEnrollment enrollment = studentEnrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> notFound.get(ENROLLMENT_NOT_FOUND, enrollmentId));
        if(!enrollment.getStudent().getId().equals(student.getId())){
            throw notAllowed.get();
        }
        if(enrollment.getGroup() == null)
            return;
        CourseGroup group = enrollment.getGroup();
        group.setNumEnrolled(group.getNumEnrolled() - 1);
        courseGroupRepository.save(group);
        enrollment.setGroup(null);
        studentEnrollmentRepository.save(enrollment);
    }
}
