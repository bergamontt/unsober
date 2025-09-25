package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.StudentRequestDto;
import ua.unsober.backend.dtos.response.StudentResponseDto;
import ua.unsober.backend.service.StudentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public StudentResponseDto create(@Valid @RequestBody StudentRequestDto dto) {
        return studentService.create(dto);
    }

    @GetMapping
    public List<StudentResponseDto> getAll() {
        return studentService.getAll();
    }

    @GetMapping("/{id}")
    public StudentResponseDto getById(@PathVariable UUID id) {
        return studentService.getById(id);
    }

    @PatchMapping("/{id}")
    public StudentResponseDto update(
            @PathVariable UUID id,
            @RequestBody StudentRequestDto dto) {
        return studentService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        studentService.delete(id);
    }
}
