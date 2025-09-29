package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.CourseClassRequestDto;
import ua.unsober.backend.dtos.response.CourseClassResponseDto;
import ua.unsober.backend.service.CourseClassService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/course-classes")
@RequiredArgsConstructor
public class CourseClassController {

    private final CourseClassService courseClassService;

    @PostMapping
    public CourseClassResponseDto create(@Valid @RequestBody CourseClassRequestDto dto) {
        return courseClassService.create(dto);
    }

    @GetMapping
    public List<CourseClassResponseDto> getAll() {
        return courseClassService.getAll();
    }

    @GetMapping("/{id}")
    public CourseClassResponseDto getById(@PathVariable UUID id) {
        return courseClassService.getById(id);
    }

    @PatchMapping("/{id}")
    public CourseClassResponseDto update(
            @PathVariable UUID id,
            @RequestBody CourseClassRequestDto dto) {
        return courseClassService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        courseClassService.delete(id);
    }
}

