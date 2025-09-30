package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.WithdrawalRequestRequestDto;
import ua.unsober.backend.dtos.response.StudentEnrollmentResponseDto;
import ua.unsober.backend.dtos.response.WithdrawalRequestResponseDto;
import ua.unsober.backend.enums.RequestStatus;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/withdrawal-request")
public class WithdrawalRequestController {

    @PostMapping
    public WithdrawalRequestResponseDto create(@Valid @RequestBody WithdrawalRequestRequestDto dto) {
        return WithdrawalRequestResponseDto.builder()
                .id(UUID.randomUUID())
                .studentEnrollment(new StudentEnrollmentResponseDto())
                .reason(dto.getReason())
                .createdAt(Instant.now())
                .build();
    }

    @GetMapping
    public List<WithdrawalRequestResponseDto> getAll() {
        return List.of(
                WithdrawalRequestResponseDto.builder()
                        .id(UUID.randomUUID())
                        .studentEnrollment(new StudentEnrollmentResponseDto())
                        .reason("Відповідно до особистих обставин")
                        .status(RequestStatus.PENDING)
                        .createdAt(Instant.now())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public WithdrawalRequestResponseDto getById(@PathVariable UUID id) {
        return WithdrawalRequestResponseDto.builder()
                .id(id)
                .studentEnrollment(new StudentEnrollmentResponseDto())
                .reason("Відповідно до особистих обставин")
                .status(RequestStatus.PENDING)
                .createdAt(Instant.now())
                .build();
    }

    @PatchMapping("/{id}")
    public WithdrawalRequestResponseDto update(
            @PathVariable UUID id,
            @RequestBody WithdrawalRequestRequestDto dto) {
        return WithdrawalRequestResponseDto.builder()
                .id(id)
                .studentEnrollment(new StudentEnrollmentResponseDto())
                .reason(Optional.ofNullable(dto.getReason()).orElse("Відповідно до особистих обставин"))
                .status(RequestStatus.PENDING)
                .createdAt(Instant.now())
                .build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {}

}
