package ua.unsober.backend.service;

import ua.unsober.backend.dtos.request.SpecialtyRequestDto;
import ua.unsober.backend.dtos.response.SpecialityResponseDto;
import ua.unsober.backend.entities.Specialty;

import java.util.List;
import java.util.UUID;

public interface SpecialityService {
    SpecialityResponseDto create(SpecialtyRequestDto dto);
    List<Specialty> getAll();
    SpecialityResponseDto getById(UUID id);
    SpecialityResponseDto update(UUID id, SpecialtyRequestDto dto);
    void delete(UUID id);
}