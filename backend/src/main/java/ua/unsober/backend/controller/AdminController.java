package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.AdminRequestDto;
import ua.unsober.backend.dtos.response.AdminResponseDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @PostMapping
    public AdminResponseDto create(@Valid @RequestBody AdminRequestDto dto) {
        return AdminResponseDto.builder()
                .id(UUID.randomUUID())
                .email("example@example.com")
                .build();
    }

    @GetMapping
    public List<AdminResponseDto> getAll() {
        return List.of(
                AdminResponseDto.builder()
                        .id(UUID.randomUUID())
                        .email("example@example.com")
                        .build()
        );
    }

    @GetMapping("/{id}")
    public AdminResponseDto getById(@PathVariable UUID id) {
        return AdminResponseDto.builder()
                .id(id)
                .email("example@example.com")
                .build();
    }

    @PatchMapping("/{id}")
    public AdminResponseDto update(
            @PathVariable UUID id,
            @RequestBody AdminRequestDto dto) {
        return AdminResponseDto.builder()
                .id(id)
                .email(dto.getEmail() == null ? "example@example.com" : dto.getEmail())
                .build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
    }
}
