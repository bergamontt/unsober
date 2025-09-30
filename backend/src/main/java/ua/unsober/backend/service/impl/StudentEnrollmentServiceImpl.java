package ua.unsober.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.unsober.backend.dtos.request.StudentEnrollmentRequestDto;
import ua.unsober.backend.dtos.response.StudentEnrollmentResponseDto;
import ua.unsober.backend.entities.Course;
import ua.unsober.backend.entities.CourseGroup;
import ua.unsober.backend.entities.StudentEnrollment;
import ua.unsober.backend.exceptions.CourseFullException;
import ua.unsober.backend.exceptions.GroupFullException;
import ua.unsober.backend.exceptions.LocalizedEntityNotFoundExceptionFactory;
import ua.unsober.backend.mapper.request.StudentEnrollmentRequestMapper;
import ua.unsober.backend.mapper.response.StudentEnrollmentResponseMapper;
import ua.unsober.backend.repository.CourseGroupRepository;
import ua.unsober.backend.repository.CourseRepository;
import ua.unsober.backend.repository.StudentEnrollmentRepository;
import ua.unsober.backend.service.StudentEnrollmentService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentEnrollmentServiceImpl implements StudentEnrollmentService {

    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final CourseRepository courseRepository;
    private final CourseGroupRepository courseGroupRepository;
    private final StudentEnrollmentRequestMapper requestMapper;
    private final StudentEnrollmentResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    private Course validateCourse(UUID courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> notFound.get("error.course.notfound", courseId));

        if (course.getNumEnrolled() >= course.getMaxStudents())
            throw new CourseFullException("Course with id " + course.getId() + " is full");

        return course;
    }

    private CourseGroup validateGroup(UUID groupId) {
        CourseGroup courseGroup = courseGroupRepository.findById(groupId)
                .orElseThrow(() -> notFound.get("error.course-group.notfound", groupId));

        if (courseGroup.getNumEnrolled() >= courseGroup.getMaxStudents())
            throw new GroupFullException("Group with id " + courseGroup.getId() + " is full");

        return courseGroup;
    }

    @Override
    public StudentEnrollmentResponseDto create(StudentEnrollmentRequestDto dto) {
        Course course = validateCourse(dto.getCourseId());
        CourseGroup courseGroup = null;

        if(dto.getGroupId() != null){
            courseGroup = validateGroup(dto.getGroupId());
        }

        StudentEnrollment enrollment = requestMapper.toEntity(dto);
        enrollment.setCourse(course);
        enrollment.setGroup(courseGroup);

        course.setNumEnrolled(course.getNumEnrolled() + 1);
        if (courseGroup != null)
            courseGroup.setNumEnrolled(courseGroup.getNumEnrolled() + 1);

        return responseMapper.toDto(studentEnrollmentRepository.save(enrollment));
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
        return responseMapper.toDto(
                studentEnrollmentRepository.findById(id).orElseThrow(
                        () -> notFound.get("error.student-enrollment.notfound", id)
                )
        );
    }

    @Override
    public StudentEnrollmentResponseDto update(UUID id, StudentEnrollmentRequestDto dto) {
        StudentEnrollment enrollment = studentEnrollmentRepository.findById(id).orElseThrow(() ->
                notFound.get("error.student-enrollment.notfound", id));

        if (dto.getCourseId() != null)
            enrollment.setCourse(validateCourse(dto.getCourseId()));
        if (dto.getGroupId() != null)
            enrollment.setGroup(validateGroup(dto.getGroupId()));

        StudentEnrollment newEnrollment = requestMapper.toEntity(dto);

        if (newEnrollment.getStudent() != null)
            enrollment.setStudent(newEnrollment.getStudent());
        if (newEnrollment.getStatus() != null)
            enrollment.setStatus(newEnrollment.getStatus());
        if (newEnrollment.getEnrollmentYear() != null)
            enrollment.setEnrollmentYear(newEnrollment.getEnrollmentYear());

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

}
