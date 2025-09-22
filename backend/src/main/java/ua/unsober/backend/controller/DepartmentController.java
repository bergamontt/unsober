package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.DepartmentRequestDto;

import java.util.UUID;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @PostMapping
    public DepartmentRequestDto create(@Valid @RequestBody DepartmentRequestDto dto) {
        return dto;
    }

    @GetMapping
    public DepartmentRequestDto[] getAll() {
        return new DepartmentRequestDto[]{
                new DepartmentRequestDto(
                        UUID.randomUUID(),
                        "Sample Department",
                        "Sample description."
                )
        };
    }

    @GetMapping("/{id}")
    public DepartmentRequestDto getById(@PathVariable UUID id) {
        return new DepartmentRequestDto(
                UUID.randomUUID(),
                "Sample Department",
                "Sample description."
        );
    }

    @PutMapping("/{id}")
    public DepartmentRequestDto replace(
            @PathVariable UUID id,
            @Valid @RequestBody DepartmentRequestDto dto) {
        return dto;
    }

    @PatchMapping("/{id}")
    public DepartmentRequestDto update(
            @PathVariable UUID id,
            @RequestBody DepartmentRequestDto dto) {
        return dto;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {}
}
