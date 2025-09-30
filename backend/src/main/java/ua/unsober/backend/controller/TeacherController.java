
package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.TeacherRequestDto;
import ua.unsober.backend.dtos.response.TeacherResponseDto;
import ua.unsober.backend.service.TeacherService;

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
    public List<TeacherResponseDto> getAll() {
        return teacherService.getAll();
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
