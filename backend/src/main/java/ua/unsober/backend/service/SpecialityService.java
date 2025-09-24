package ua.unsober.backend.service;

import ua.unsober.backend.dtos.request.SpecialityRequestDto;
import ua.unsober.backend.dtos.response.SpecialityResponseDto;

import java.util.List;
import java.util.UUID;

public interface SpecialityService {
    SpecialityResponseDto create(SpecialityRequestDto dto);
    List<SpecialityResponseDto> getAll();
    SpecialityResponseDto getById(UUID id);
    SpecialityResponseDto update(UUID id, SpecialityRequestDto dto);
    void delete(UUID id);
}