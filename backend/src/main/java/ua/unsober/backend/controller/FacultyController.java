package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.FacultyRequestDto;
import ua.unsober.backend.dtos.response.FacultyResponseDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    @PostMapping
    public FacultyResponseDto create(@Valid @RequestBody FacultyRequestDto dto) {
        return FacultyResponseDto.builder()
                .id(UUID.randomUUID())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    @GetMapping
    public List<FacultyResponseDto> getAll() {
        return List.of(
                FacultyResponseDto.builder()
                        .id(UUID.randomUUID())
                        .name("Кафедра математики")
                        .description("Кафедра математики.")
                        .build()
        );
    }

    @GetMapping("/{id}")
    public FacultyResponseDto getById(@PathVariable UUID id) {
        return FacultyResponseDto.builder()
                .id(id)
                .name("Кафедра математики")
                .description("Кафедра математики.")
                .build();
    }

    @PatchMapping("/{id}")
    public FacultyResponseDto update(
            @PathVariable UUID id,
            @RequestBody FacultyRequestDto dto) {
        return FacultyResponseDto.builder()
                .id(id)
                .name(dto.getName() == null ? "Кафедра математики" : dto.getName())
                .description(dto.getDescription() == null ? "Кафедра математики." : dto.getDescription())
                .build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {}
}

