package ua.unsober.backend.service;

import ua.unsober.backend.dtos.request.DepartmentRequestDto;
import ua.unsober.backend.dtos.response.DepartmentResponseDto;

import java.util.List;
import java.util.UUID;

public interface DepartmentService {
    DepartmentResponseDto create(DepartmentRequestDto dto);
    List<DepartmentResponseDto> getAll();
    DepartmentResponseDto getById(UUID id);
    DepartmentResponseDto update(UUID id, DepartmentRequestDto dto);
    void delete(UUID id);
}
