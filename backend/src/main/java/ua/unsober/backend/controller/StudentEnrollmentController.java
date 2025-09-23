package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.StudentEnrollmentRequestDto;
import ua.unsober.backend.dtos.response.CourseGroupResponseDto;
import ua.unsober.backend.dtos.response.CourseResponseDto;
import ua.unsober.backend.dtos.response.StudentEnrollmentResponseDto;
import ua.unsober.backend.dtos.response.StudentResponseDto;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/student-enrollments")
public class StudentEnrollmentController {

    @PostMapping
    public StudentEnrollmentResponseDto create(@Valid @RequestBody StudentEnrollmentRequestDto dto) {
        return StudentEnrollmentResponseDto.builder()
                .id(UUID.randomUUID())
                .student(new StudentResponseDto())
                .course(new CourseResponseDto())
                .courseGroup(new CourseGroupResponseDto())
                .status(dto.getStatus())
                .enrollmentYear(dto.getEnrollmentYear())
                .createdAt(Instant.now())
                .build();
    }

    @GetMapping
    public List<StudentEnrollmentResponseDto> getAll() {
        return List.of(
                StudentEnrollmentResponseDto.builder()
                        .id(UUID.randomUUID())
                        .student(new StudentResponseDto())
                        .course(new CourseResponseDto())
                        .courseGroup(new CourseGroupResponseDto())
                        .status("active")
                        .enrollmentYear(2025)
                        .createdAt(Instant.now())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public StudentEnrollmentResponseDto getById(@PathVariable UUID id) {
        return StudentEnrollmentResponseDto.builder()
                .id(id)
                .student(new StudentResponseDto())
                .course(new CourseResponseDto())
                .courseGroup(new CourseGroupResponseDto())
                .status("active")
                .enrollmentYear(2025)
                .createdAt(Instant.now())
                .build();
    }

    @PatchMapping("/{id}")
    public StudentEnrollmentResponseDto update(
            @PathVariable UUID id,
            @RequestBody StudentEnrollmentRequestDto dto) {
        return StudentEnrollmentResponseDto.builder()
                .id(id)
                .student(new StudentResponseDto())
                .course(new CourseResponseDto())
                .courseGroup(new CourseGroupResponseDto())
                .status(Optional.ofNullable(dto.getStatus()).orElse("active"))
                .enrollmentYear(Optional.ofNullable(dto.getEnrollmentYear()).orElse(2025))
                .createdAt(Instant.now())
                .build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {}

}
