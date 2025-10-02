package ua.unsober.backend.feature.faculty;

import java.util.List;
import java.util.UUID;

public interface FacultyService {
    FacultyResponseDto create(FacultyRequestDto dto);
    List<FacultyResponseDto> getAll();
    FacultyResponseDto getById(UUID id);
    FacultyResponseDto update(UUID id, FacultyRequestDto dto);
    void delete(UUID id);
}
