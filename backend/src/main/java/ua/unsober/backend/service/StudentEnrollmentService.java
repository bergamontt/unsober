package ua.unsober.backend.service;

import ua.unsober.backend.dtos.request.StudentEnrollmentRequestDto;
import ua.unsober.backend.dtos.response.StudentEnrollmentResponseDto;

import java.util.List;
import java.util.UUID;

public interface StudentEnrollmentService {
    StudentEnrollmentResponseDto create(StudentEnrollmentRequestDto dto);
    List<StudentEnrollmentResponseDto> getAll();
    StudentEnrollmentResponseDto getById(UUID id);
    StudentEnrollmentResponseDto update(UUID id, StudentEnrollmentRequestDto dto);
    void delete(UUID id);
}
