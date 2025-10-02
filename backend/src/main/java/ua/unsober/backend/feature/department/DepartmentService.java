package ua.unsober.backend.feature.department;

import java.util.List;
import java.util.UUID;

public interface DepartmentService {
    DepartmentResponseDto create(DepartmentRequestDto dto);
    List<DepartmentResponseDto> getAll();
    DepartmentResponseDto getById(UUID id);
    DepartmentResponseDto update(UUID id, DepartmentRequestDto dto);
    void delete(UUID id);
}
