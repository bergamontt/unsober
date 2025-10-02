package ua.unsober.backend.feature.student;

import java.util.List;
import java.util.UUID;

public interface StudentService {
    StudentResponseDto create(StudentRequestDto dto);
    List<StudentResponseDto> getAll();
    StudentResponseDto getById(UUID id);
    StudentResponseDto update(UUID id, StudentRequestDto dto);
    void delete(UUID id);
}