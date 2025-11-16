package ua.unsober.backend.feature.student;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.common.aspects.retry.Retryable;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/student")
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

    @GetMapping("/uuid/{id}")
    public StudentResponseDto getById(@PathVariable UUID id) {
        return studentService.getById(id);
    }

    @GetMapping("/email/{email}")
    public StudentResponseDto getByEmail(@PathVariable String email) {
        return studentService.getByEmail(email);
    }

    @PatchMapping("/{id}")
    @Retryable
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
