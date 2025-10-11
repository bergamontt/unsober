package ua.unsober.backend.feature.studentEnrollment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
