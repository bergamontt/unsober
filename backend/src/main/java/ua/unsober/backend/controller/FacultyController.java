package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.FacultyRequestDto;

import java.util.UUID;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    @PostMapping
    public FacultyRequestDto create(@Valid @RequestBody FacultyRequestDto dto) {
        return dto;
    }

    @GetMapping
    public FacultyRequestDto[] getAll() {
        return new FacultyRequestDto[]{
                new FacultyRequestDto(
                        "Sample Faculty",
                        "Sample description."
                )
        };
    }

    @GetMapping("/{id}")
    public FacultyRequestDto getById(@PathVariable UUID id) {
        return new FacultyRequestDto(
                "Sample Faculty",
                "Sample description."
        );
    }

    @PutMapping("/{id}")
    public FacultyRequestDto replace(
            @PathVariable UUID id,
            @Valid @RequestBody FacultyRequestDto dto) {
        return dto;
    }

    @PatchMapping("/{id}")
    public FacultyRequestDto update(
            @PathVariable UUID id,
            @RequestBody FacultyRequestDto dto) {
        return dto;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {}
}

