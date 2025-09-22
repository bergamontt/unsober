package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.CourseGroupRequestDto;

import java.util.UUID;

@RestController
@RequestMapping("/course-groups")
public class CourseGroupController {

    @PostMapping
    public CourseGroupRequestDto create(@Valid @RequestBody CourseGroupRequestDto dto) {
        return dto;
    }

    @GetMapping
    public CourseGroupRequestDto[] getAll() {
        return new CourseGroupRequestDto[]{
                new CourseGroupRequestDto(
                        UUID.randomUUID(),
                        1,
                        30
                )
        };
    }

    @GetMapping("/{id}")
    public CourseGroupRequestDto getById(@PathVariable UUID id) {
        return new CourseGroupRequestDto(
                UUID.randomUUID(),
                1,
                30
        );
    }

    @PutMapping("/{id}")
    public CourseGroupRequestDto replace(
            @PathVariable UUID id,
            @Valid @RequestBody CourseGroupRequestDto dto) {
        return dto;
    }

    @PatchMapping("/{id}")
    public CourseGroupRequestDto update(
            @PathVariable UUID id,
            @RequestBody CourseGroupRequestDto dto) {
        return dto;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {}
}

