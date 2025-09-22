package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.AdminRequestDto;

import java.util.UUID;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @PostMapping
    public AdminRequestDto create(@Valid @RequestBody AdminRequestDto dto) {
        return dto;
    }

    @GetMapping
    public AdminRequestDto[] getAll() {
        return new AdminRequestDto[]{
                new AdminRequestDto(
                        "example@example.com",
                        "password"
                )
        };
    }

    @GetMapping("/{id}")
    public AdminRequestDto getById(@PathVariable UUID id) {
        return new AdminRequestDto(
                "example@example.com",
                "password"
        );
    }

    @PutMapping("/{id}")
    public AdminRequestDto replace(
            @PathVariable UUID id,
            @Valid @RequestBody AdminRequestDto dto) {
        return dto;
    }

    @PatchMapping("/{id}")
    public AdminRequestDto update(
            @PathVariable UUID id,
            @RequestBody AdminRequestDto dto) {
        return dto;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
    }
}
