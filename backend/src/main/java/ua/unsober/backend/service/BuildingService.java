package ua.unsober.backend.service;

import ua.unsober.backend.dtos.request.BuildingRequestDto;
import ua.unsober.backend.dtos.response.BuildingResponseDto;

import java.util.List;
import java.util.UUID;

public interface BuildingService {
    BuildingResponseDto create(BuildingRequestDto dto);
    List<BuildingResponseDto> getAll();
    BuildingResponseDto getById(UUID id);
    BuildingResponseDto update(UUID id, BuildingRequestDto dto);
    void delete(UUID id);
}
