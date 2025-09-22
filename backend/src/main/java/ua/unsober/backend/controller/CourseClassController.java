package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.CourseClassRequestDto;

import java.util.UUID;

@RestController
@RequestMapping("/course-classes")
public class CourseClassController {

    @PostMapping
    public CourseClassRequestDto create(@Valid @RequestBody CourseClassRequestDto dto) {
        return dto;
    }

    @GetMapping
    public CourseClassRequestDto[] getAll() {
        return new CourseClassRequestDto[]{
                new CourseClassRequestDto(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        "Sample Class",
                        "Lecture",
                        new Integer[]{1, 2, 3},
                        "Monday",
                        1,
                        "Room 101",
                        UUID.randomUUID()
                )
        };
    }

    @GetMapping("/{id}")
    public CourseClassRequestDto getById(@PathVariable UUID id) {
        return new CourseClassRequestDto(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Sample Class",
                "Lecture",
                new Integer[]{1, 2, 3},
                "Monday",
                1,
                "313",
                UUID.randomUUID()
        );
    }

    @PutMapping("/{id}")
    public CourseClassRequestDto replace(
            @PathVariable UUID id,
            @Valid @RequestBody CourseClassRequestDto dto) {
        return dto;
    }

    @PatchMapping("/{id}")
    public CourseClassRequestDto update(
            @PathVariable UUID id,
            @RequestBody CourseClassRequestDto dto) {
        return dto;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {}
}

