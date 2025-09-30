package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.SpecialityRequestDto;
import ua.unsober.backend.dtos.response.DepartmentResponseDto;
import ua.unsober.backend.dtos.response.SpecialityResponseDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/speciality")
public class SpecialityController {

    @PostMapping
    public SpecialityResponseDto create(@Valid @RequestBody SpecialityRequestDto dto) {
        return SpecialityResponseDto.builder()
                .id(UUID.randomUUID())
                .department(new DepartmentResponseDto())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    @GetMapping
    public List<SpecialityResponseDto> getAll() {
        return List.of(
                SpecialityResponseDto.builder()
                        .id(UUID.randomUUID())
                        .department(new DepartmentResponseDto())
                        .name("Прикладна математика")
                        .description("description: bla-bla")
                        .build()
        );
    }

    @GetMapping("/{id}")
    public SpecialityResponseDto getById(@PathVariable UUID id) {
        return SpecialityResponseDto.builder()
                .id(id)
                .department(new DepartmentResponseDto())
                .name("Прикладна математика")
                .description("description: bla-bla")
                .build();
    }

    @PatchMapping("/{id}")
    public SpecialityResponseDto update(
            @PathVariable UUID id,
            @RequestBody SpecialityRequestDto dto) {
        return SpecialityResponseDto.builder()
                .id(id)
                .department(new DepartmentResponseDto())
                .name(Optional.ofNullable(dto.getName()).orElse("Прикладна математика"))
                .description(Optional.ofNullable(dto.getDescription()).orElse("description: bla-bla"))
                .build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {}

}
