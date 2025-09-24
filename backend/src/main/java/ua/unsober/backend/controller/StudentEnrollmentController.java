package ua.unsober.backend.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.StudentEnrollmentRequestDto;
import ua.unsober.backend.dtos.response.*;
import ua.unsober.backend.exceptions.CourseFullException;
import ua.unsober.backend.exceptions.GroupFullException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/student-enrollments")
public class StudentEnrollmentController {
    private final CourseResponseDto course;

    private final CourseGroupResponseDto courseGroup;

    private final List<StudentEnrollmentResponseDto> studentEnrollments = new ArrayList<>();

    StudentEnrollmentController() {
        course = CourseResponseDto.builder()
                .id(UUID.fromString("b3a93d82-1e89-4019-be34-ede0316adfb9"))
                .subject(new SubjectResponseDto())
                .maxStudents(10)
                .numEnrolled(0)
                .courseYear(2025)
                .build();
        courseGroup = CourseGroupResponseDto.builder()
                .id(UUID.fromString("b3a93d82-1e89-1234-be34-ede0316adfb9"))
                .course(course)
                .maxStudents(10)
                .groupNumber(1)
                .numEnrolled(0)
                .build();

    }

    void validateCourse(StudentEnrollmentRequestDto dto){
        if(dto.getCourseId() != null){
            if(dto.getCourseId() != course.getId())
                throw new EntityNotFoundException("Course with id " + dto.getCourseId() + " not found");
            if(course.getNumEnrolled() >= course.getMaxStudents())
                throw new CourseFullException("Course with id " + course.getId() + " is full");
        }
        if(dto.getGroupId() != null){
            if(dto.getGroupId() != courseGroup.getId())
                throw new EntityNotFoundException("Group with id " + dto.getGroupId() + " not found");
            if(courseGroup.getNumEnrolled() >= courseGroup.getMaxStudents())
                throw new GroupFullException("Group with id " + course.getId() + " is full");
        }
    }

    @PostMapping
    public StudentEnrollmentResponseDto create(@Valid @RequestBody StudentEnrollmentRequestDto dto) {
        validateCourse(dto);
        StudentEnrollmentResponseDto enrollment = StudentEnrollmentResponseDto.builder()
                .id(UUID.randomUUID())
                .enrollmentYear(1)
                .student(new StudentResponseDto())
                .course(course)
                .courseGroup(courseGroup)
                .createdAt(Instant.now())
                .build();
        course.setNumEnrolled(course.getNumEnrolled() + 1);
        courseGroup.setNumEnrolled(courseGroup.getNumEnrolled() + 1);
        studentEnrollments.add(enrollment);
        return enrollment;
    }

    @GetMapping
    public List<StudentEnrollmentResponseDto> getAll() {
        return studentEnrollments;
    }

    @GetMapping("/{id}")
    public StudentEnrollmentResponseDto getById(@PathVariable UUID id) {
        for (StudentEnrollmentResponseDto enrollment : studentEnrollments) {
            if (enrollment.getId().equals(id)) {
                return enrollment;
            }
        }
        throw new EntityNotFoundException("Student with id " + id + " not found");
    }

    @PatchMapping("/{id}")
    public StudentEnrollmentResponseDto update(
            @PathVariable UUID id,
            @RequestBody StudentEnrollmentRequestDto dto) {
        validateCourse(dto);
        StudentEnrollmentResponseDto enrollment = getById(id);
        if(dto.getGroupId() != null){
            if(enrollment.getCourseGroup() == null)
                courseGroup.setNumEnrolled(courseGroup.getNumEnrolled() + 1);
            enrollment.setCourseGroup(courseGroup);
        }
        if(dto.getEnrollmentYear() != null)
            enrollment.setEnrollmentYear(course.getNumEnrolled());
        return enrollment;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        if (studentEnrollments.stream().anyMatch(e -> e.getId().equals(id)))
            studentEnrollments.removeIf(e -> e.getId().equals(id));
        else
            throw new EntityNotFoundException("Enrollment with id " + id + " not found");
        courseGroup.setNumEnrolled(courseGroup.getNumEnrolled() - 1);
        course.setNumEnrolled(course.getNumEnrolled() - 1);
    }
}
