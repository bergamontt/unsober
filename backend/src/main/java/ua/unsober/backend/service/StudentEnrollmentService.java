package ua.unsober.backend.service;

import ua.unsober.backend.dtos.request.StudentRequestDto;
import ua.unsober.backend.dtos.response.StudentEnrollmentResponseDto;

import java.util.List;
import java.util.UUID;

public interface StudentEnrollmentService {
    StudentEnrollmentResponseDto create(StudentRequestDto dto);
    List<StudentEnrollmentResponseDto> getAll();
    StudentEnrollmentResponseDto getById(UUID id);
    StudentEnrollmentResponseDto update(UUID id, StudentRequestDto dto);
    void delete(UUID id);
}
