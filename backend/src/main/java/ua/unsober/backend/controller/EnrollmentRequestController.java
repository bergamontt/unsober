package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.EnrollmentRequestRequestDto;

import java.util.UUID;

@RestController
@RequestMapping("/enrollment-requests")
public class EnrollmentRequestController {

    @PostMapping
    public EnrollmentRequestRequestDto create(@Valid @RequestBody EnrollmentRequestRequestDto dto) {
        return dto;
    }

    @GetMapping
    public EnrollmentRequestRequestDto[] getAll() {
        return new EnrollmentRequestRequestDto[]{
                new EnrollmentRequestRequestDto(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        "Sample reason",
                        "Pending"
                )
        };
    }

    @GetMapping("/{id}")
    public EnrollmentRequestRequestDto getById(@PathVariable UUID id) {
        return new EnrollmentRequestRequestDto(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Sample reason",
                "Pending"
        );
    }

    @PutMapping("/{id}")
    public EnrollmentRequestRequestDto replace(
            @PathVariable UUID id,
            @Valid @RequestBody EnrollmentRequestRequestDto dto) {
        return dto;
    }

    @PatchMapping("/{id}")
    public EnrollmentRequestRequestDto update(
            @PathVariable UUID id,
            @RequestBody EnrollmentRequestRequestDto dto) {
        return dto;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {}
}
