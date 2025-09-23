package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.DepartmentRequestDto;
import ua.unsober.backend.dtos.response.DepartmentResponseDto;
import ua.unsober.backend.dtos.response.FacultyResponseDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @PostMapping
    public DepartmentResponseDto create(@Valid @RequestBody DepartmentRequestDto dto) {
        return DepartmentResponseDto.builder()
                .id(UUID.randomUUID())
                .faculty(new FacultyResponseDto())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    @GetMapping
    public List<DepartmentResponseDto> getAll() {
        return List.of(
                DepartmentResponseDto.builder()
                        .id(UUID.randomUUID())
                        .faculty(new FacultyResponseDto())
                        .name("Факультет інформатики")
                        .description("Найкращий факультет!")
                        .build()
        );
    }

    @GetMapping("/{id}")
    public DepartmentResponseDto getById(@PathVariable UUID id) {
        return DepartmentResponseDto.builder()
                .id(id)
                .faculty(new FacultyResponseDto())
                .name("Факультет інформатики")
                .description("Найкращий факультет!")
                .build();
    }

    @PatchMapping("/{id}")
    public DepartmentResponseDto update(
            @PathVariable UUID id,
            @RequestBody DepartmentRequestDto dto) {
        return DepartmentResponseDto.builder()
                .id(id)
                .faculty(new FacultyResponseDto())
                .name(dto.getName() == null ? "Факультет інформатики" : dto.getName())
                .description(dto.getDescription() == null ? "Найкращий факультет!" : dto.getDescription())
                .build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {}
}
