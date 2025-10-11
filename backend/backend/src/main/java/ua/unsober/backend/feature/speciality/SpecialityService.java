package ua.unsober.backend.feature.speciality;

import java.util.List;
import java.util.UUID;

public interface SpecialityService {
    SpecialityResponseDto create(SpecialityRequestDto dto);
    List<SpecialityResponseDto> getAll();
    SpecialityResponseDto getById(UUID id);
    SpecialityResponseDto update(UUID id, SpecialityRequestDto dto);
    void delete(UUID id);
}