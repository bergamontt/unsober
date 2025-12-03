
package ua.unsober.backend.feature.teacher;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    public TeacherResponseDto create(@Valid @RequestBody TeacherRequestDto dto) {
        return teacherService.create(dto);
    }

    @GetMapping
    public List<TeacherResponseDto> getAll(@ModelAttribute TeacherFilterDto filters) {
        return teacherService.getAll(filters);
    }

    @GetMapping("/{id}")
    public TeacherResponseDto getById(@PathVariable UUID id) {
        return teacherService.getById(id);
    }

    @PatchMapping("/{id}")
    public TeacherResponseDto update(
            @PathVariable UUID id,
            @RequestBody TeacherRequestDto dto) {
        return teacherService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        teacherService.delete(id);
    }
}
