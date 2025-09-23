package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.CourseRequestDto;

import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @PostMapping
    public CourseRequestDto create(@Valid @RequestBody CourseRequestDto dto) {
        return dto;
    }

    @GetMapping
    public CourseRequestDto[] getAll() {
        return new CourseRequestDto[]{
                new CourseRequestDto(
                        UUID.randomUUID(),
                        50,
                        20,
                        2025
                )
        };
    }

    @GetMapping("/{id}")
    public CourseRequestDto getById(@PathVariable UUID id) {
        return new CourseRequestDto(
                UUID.randomUUID(),
                50,
                20,
                2025
        );
    }

    @PutMapping("/{id}")
    public CourseRequestDto replace(
            @PathVariable UUID id,
            @Valid @RequestBody CourseRequestDto dto) {
        return dto;
    }

    @PatchMapping("/{id}")
    public CourseRequestDto update(
            @PathVariable UUID id,
            @RequestBody CourseRequestDto dto) {
        return dto;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {}
}
