package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.CourseRequestDto;
import ua.unsober.backend.dtos.response.CourseResponseDto;
import ua.unsober.backend.dtos.response.SubjectResponseDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @PostMapping
    public CourseResponseDto create(@Valid @RequestBody CourseRequestDto dto) {
        return CourseResponseDto.builder()
                .id(UUID.randomUUID())
                .subject(new SubjectResponseDto())
                .maxStudents(dto.getMaxStudents())
                .numEnrolled(0)
                .courseYear(dto.getCourseYear())
                .build();
    }

    @GetMapping
    public List<CourseResponseDto> getAll() {
        return List.of(
                CourseResponseDto.builder()
                        .id(UUID.randomUUID())
                        .subject(new SubjectResponseDto())
                        .maxStudents(40)
                        .numEnrolled(10)
                        .courseYear(2025)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public CourseResponseDto getById(@PathVariable UUID id) {
        return CourseResponseDto.builder()
                .id(id)
                .subject(new SubjectResponseDto())
                .maxStudents(40)
                .numEnrolled(10)
                .courseYear(2025)
                .build();
    }

    @PatchMapping("/{id}")
    public CourseResponseDto update(
            @PathVariable UUID id,
            @RequestBody CourseRequestDto dto) {
        return CourseResponseDto.builder()
                .id(id)
                .subject(new SubjectResponseDto())
                .maxStudents(dto.getMaxStudents() == null ? 40 : dto.getMaxStudents())
                .numEnrolled(10)
                .courseYear(dto.getCourseYear() == null ? 2025 : dto.getCourseYear())
                .build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
    }
}
