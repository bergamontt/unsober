package ua.unsober.backend.service;

import ua.unsober.backend.dtos.request.FacultyRequestDto;
import ua.unsober.backend.dtos.response.FacultyResponseDto;

import java.util.List;
import java.util.UUID;

public interface FacultyService {
    FacultyResponseDto create(FacultyRequestDto dto);
    List<FacultyResponseDto> getAll();
    FacultyResponseDto getById(UUID id);
    FacultyResponseDto update(UUID id, FacultyRequestDto dto);
    void delete(UUID id);
}
