package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.EnrollmentRequestRequestDto;
import ua.unsober.backend.dtos.response.EnrollmentRequestResponseDto;
import ua.unsober.backend.service.EnrollmentRequestService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/enrollment-requests")
@RequiredArgsConstructor
public class EnrollmentRequestController {

    private final EnrollmentRequestService enrollmentRequestService;

    @PostMapping
    public EnrollmentRequestResponseDto create(@Valid @RequestBody EnrollmentRequestRequestDto dto) {
        return enrollmentRequestService.create(dto);
    }

    @GetMapping
    public List<EnrollmentRequestResponseDto> getAll() {
        return enrollmentRequestService.getAll();
    }

    @GetMapping("/{id}")
    public EnrollmentRequestResponseDto getById(@PathVariable UUID id) {
        return enrollmentRequestService.getById(id);
    }

    @PatchMapping("/{id}")
    public EnrollmentRequestResponseDto update(
            @PathVariable UUID id,
            @RequestBody EnrollmentRequestRequestDto dto) {
        return enrollmentRequestService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        enrollmentRequestService.delete(id);
    }
}

