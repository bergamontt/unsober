package ua.unsober.backend.feature.teacher;

import java.util.List;
import java.util.UUID;

public interface TeacherService {
    TeacherResponseDto create(TeacherRequestDto dto);
    List<TeacherResponseDto> getAll(TeacherFilterDto filters);
    TeacherResponseDto getById(UUID id);
    TeacherResponseDto update(UUID id, TeacherRequestDto dto);
    void delete(UUID id);
}
