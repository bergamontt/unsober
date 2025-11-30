package ua.unsober.backend.feature.request.enrollment;

import ua.unsober.backend.common.enums.RequestStatus;

import java.util.List;
import java.util.UUID;

public interface EnrollmentRequestService {
    EnrollmentRequestResponseDto create(EnrollmentRequestRequestDto requestDto);
    List<EnrollmentRequestResponseDto> getAll();
    List<EnrollmentRequestResponseDto> getAllWithStatus(RequestStatus status);
    List<EnrollmentRequestResponseDto> getAllByStudentId(UUID studentId);
    EnrollmentRequestResponseDto getById(UUID id);
    EnrollmentRequestResponseDto update(UUID id, EnrollmentRequestRequestDto requestDto);
    EnrollmentRequestResponseDto updateStatus(UUID id, RequestStatus status);
    void delete(UUID id);
}
