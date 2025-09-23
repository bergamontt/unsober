package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.CourseGroupRequestDto;
import ua.unsober.backend.dtos.response.CourseGroupResponseDto;
import ua.unsober.backend.dtos.response.CourseResponseDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/course-groups")
public class CourseGroupController {

    @PostMapping
    public CourseGroupResponseDto create(@Valid @RequestBody CourseGroupRequestDto dto) {
        return CourseGroupResponseDto.builder()
                .id(UUID.randomUUID())
                .course(new CourseResponseDto())
                .maxStudents(dto.getMaxStudents())
                .groupNumber(dto.getGroupNumber())
                .numEnrolled(0)
                .build();
    }

    @GetMapping
    public List<CourseGroupResponseDto> getAll() {
        return List.of(
                CourseGroupResponseDto.builder()
                        .id(UUID.randomUUID())
                        .course(new CourseResponseDto())
                        .maxStudents(10)
                        .groupNumber(2)
                        .numEnrolled(8)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public CourseGroupResponseDto getById(@PathVariable UUID id) {
        return CourseGroupResponseDto.builder()
                .id(id)
                .course(new CourseResponseDto())
                .maxStudents(10)
                .groupNumber(2)
                .numEnrolled(8)
                .build();
    }

    @PatchMapping("/{id}")
    public CourseGroupResponseDto update(
            @PathVariable UUID id,
            @RequestBody CourseGroupRequestDto dto) {
        return CourseGroupResponseDto.builder()
                .id(id)
                .course(new CourseResponseDto())
                .maxStudents(dto.getMaxStudents() == null ? 10 : dto.getMaxStudents())
                .groupNumber(dto.getGroupNumber() == null ? 2 : dto.getGroupNumber())
                .numEnrolled(8)
                .build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {}
}

