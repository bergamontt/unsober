package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.StudentEnrollmentRequestDto;
import ua.unsober.backend.dtos.response.*;
import ua.unsober.backend.service.StudentEnrollmentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/student-enrollment")
@RequiredArgsConstructor
public class StudentEnrollmentController {

    private final StudentEnrollmentService studentEnrollmentService;

    @PostMapping
    public StudentEnrollmentResponseDto create(@Valid @RequestBody StudentEnrollmentRequestDto dto) {
        return studentEnrollmentService.create(dto);
    }

    @GetMapping
    public List<StudentEnrollmentResponseDto> getAll() {
        return studentEnrollmentService.getAll();
    }

    @GetMapping("/{id}")
    public StudentEnrollmentResponseDto getById(@PathVariable UUID id) {
        return studentEnrollmentService.getById(id);
    }

    @PatchMapping("/{id}")
    public StudentEnrollmentResponseDto update(
            @PathVariable UUID id,
            @RequestBody StudentEnrollmentRequestDto dto) {
        return studentEnrollmentService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        studentEnrollmentService.delete(id);
    }
}
