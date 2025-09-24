package ua.unsober.backend.mapper.response;

import org.mapstruct.Mapper;
import ua.unsober.backend.dtos.response.SpecialityResponseDto;
import ua.unsober.backend.entities.Specialty;

@Mapper(componentModel = "spring")
public interface SpecialityResponseMapper {
    SpecialityResponseDto toDto(Specialty specialty);
}
