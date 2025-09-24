package ua.unsober.backend.service;

import ua.unsober.backend.dtos.request.EnrollmentRequestRequestDto;
import ua.unsober.backend.dtos.response.EnrollmentRequestResponseDto;

import java.util.List;
import java.util.UUID;

public interface EnrollmentRequestService {
    EnrollmentRequestResponseDto create(EnrollmentRequestRequestDto requestDto);
    List<EnrollmentRequestResponseDto> getAll();
    EnrollmentRequestResponseDto getById(UUID id);
    EnrollmentRequestResponseDto update(UUID id, EnrollmentRequestRequestDto requestDto);
    void delete(UUID id);
}
