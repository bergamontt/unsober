package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.BuildingRequestDto;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/buildings")
public class BuildingController {

    @PostMapping
    public BuildingRequestDto create(@Valid @RequestBody BuildingRequestDto dto) {
        return dto;
    }

    @GetMapping
    public BuildingRequestDto[] getAll() {
        return new BuildingRequestDto[]{
                new BuildingRequestDto(
                        "Name",
                        "Address",
                        BigDecimal.ZERO,
                        BigDecimal.ZERO
                )
        };
    }

    @GetMapping("/{id}")
    public BuildingRequestDto getById(@PathVariable UUID id) {
        return new BuildingRequestDto(
                "Name",
                "Address",
                BigDecimal.ZERO,
                BigDecimal.ZERO
        );
    }

    @PutMapping("/{id}")
    public BuildingRequestDto replace(
            @PathVariable UUID id,
            @Valid @RequestBody BuildingRequestDto dto) {
        return dto;
    }

    @PatchMapping("/{id}")
    public BuildingRequestDto update(
            @PathVariable UUID id,
            @RequestBody BuildingRequestDto dto) {
        return dto;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
    }
}
