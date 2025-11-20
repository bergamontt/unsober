package ua.unsober.backend.feature.course;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
    public Page<CourseResponseDto> getAll(Pageable pageable) {
        return courseService.getAll(pageable);
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
