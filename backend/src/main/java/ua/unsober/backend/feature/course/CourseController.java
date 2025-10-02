package ua.unsober.backend.feature.course;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    public CourseResponseDto create(@Valid @RequestBody CourseRequestDto dto) {
        return courseService.create(dto);
    }

    @GetMapping
    public List<CourseResponseDto> getAll() {
        return courseService.getAll();
    }

    @GetMapping("/{id}")
    public CourseResponseDto getById(@PathVariable UUID id) {
        return courseService.getById(id);
    }

    @PatchMapping("/{id}")
    public CourseResponseDto update(
            @PathVariable UUID id,
            @RequestBody CourseRequestDto dto) {
        return courseService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        courseService.delete(id);
    }
}
