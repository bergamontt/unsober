package ua.unsober.backend.service;

import ua.unsober.backend.dtos.request.TeacherRequestDto;
import ua.unsober.backend.dtos.response.TeacherResponseDto;

import java.util.List;
import java.util.UUID;

public interface TeacherService {
    TeacherResponseDto create(TeacherRequestDto dto);
    List<TeacherResponseDto> getAll();
    TeacherResponseDto getById(UUID id);
    TeacherResponseDto update(UUID id, TeacherRequestDto dto);
    void delete(UUID id);
}
