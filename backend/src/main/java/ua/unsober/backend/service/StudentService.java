package ua.unsober.backend.service;

import ua.unsober.backend.dtos.request.StudentRequestDto;
import ua.unsober.backend.dtos.response.StudentResponseDto;

import java.util.List;
import java.util.UUID;

public interface StudentService {
    StudentResponseDto create(StudentRequestDto dto);
    List<StudentResponseDto> getAll();
    StudentResponseDto getById(UUID id);
    StudentResponseDto update(UUID id, StudentRequestDto dto);
    void delete(UUID id);
}