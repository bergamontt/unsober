package ua.unsober.backend.feature.courseGroup;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/course-group")
@RequiredArgsConstructor
public class CourseGroupController {

    private final CourseGroupService courseGroupService;

    @PostMapping
    public CourseGroupResponseDto create(@Valid @RequestBody CourseGroupRequestDto dto) {
        return courseGroupService.create(dto);
    }

    @GetMapping
    public List<CourseGroupResponseDto> getAll() {
        return courseGroupService.getAll();
    }

    @GetMapping("/{id}")
    public CourseGroupResponseDto getById(@PathVariable UUID id) {
        return courseGroupService.getById(id);
    }

    @PatchMapping("/{id}")
    public CourseGroupResponseDto update(
            @PathVariable UUID id,
            @RequestBody CourseGroupRequestDto dto) {
        return courseGroupService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        courseGroupService.delete(id);
    }
}


