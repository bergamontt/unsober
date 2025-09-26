package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.EnrollmentRequestRequestDto;
import ua.unsober.backend.dtos.response.CourseResponseDto;
import ua.unsober.backend.dtos.response.EnrollmentRequestResponseDto;
import ua.unsober.backend.dtos.response.StudentResponseDto;
import ua.unsober.backend.enums.RequestStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/enrollment-requests")
public class EnrollmentRequestController {

    @PostMapping
    public EnrollmentRequestResponseDto create(@Valid @RequestBody EnrollmentRequestRequestDto dto) {
        return EnrollmentRequestResponseDto.builder()
                .id(UUID.randomUUID())
                .course(new CourseResponseDto())
                .student(new StudentResponseDto())
                .reason(dto.getReason())
                .status(RequestStatus.PENDING)
                .createdAt(Instant.now())
                .build();
    }

    @GetMapping
    public List<EnrollmentRequestResponseDto> getAll() {
        return List.of(
                EnrollmentRequestResponseDto.builder()
                        .id(UUID.randomUUID())
                        .course(new CourseResponseDto())
                        .student(new StudentResponseDto())
                        .reason("Запишіть на інфопошук :(")
                        .status(RequestStatus.DECLINED)
                        .createdAt(Instant.now())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public EnrollmentRequestResponseDto getById(@PathVariable UUID id) {
        return EnrollmentRequestResponseDto.builder()
                .id(id)
                .course(new CourseResponseDto())
                .student(new StudentResponseDto())
                .reason("Запишіть на інфопошук :(")
                .status(RequestStatus.DECLINED)
                .createdAt(Instant.now())
                .build();
    }

    @PatchMapping("/{id}")
    public EnrollmentRequestResponseDto update(
            @PathVariable UUID id,
            @RequestBody EnrollmentRequestRequestDto dto) {
        return EnrollmentRequestResponseDto.builder()
                .id(id)
                .course(new CourseResponseDto())
                .student(new StudentResponseDto())
                .reason(dto.getReason() == null ? "Запишіть на інфопошук :(" : dto.getReason())
                .status(RequestStatus.PENDING)
                .createdAt(Instant.now())
                .build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {}
}
