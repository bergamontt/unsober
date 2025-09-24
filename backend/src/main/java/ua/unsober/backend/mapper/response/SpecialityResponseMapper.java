package ua.unsober.backend.mapper.response;

import org.mapstruct.Mapper;
import ua.unsober.backend.dtos.response.SpecialityResponseDto;
import ua.unsober.backend.entities.Speciality;

@Mapper(componentModel = "spring")
public interface SpecialityResponseMapper {
    SpecialityResponseDto toDto(Speciality speciality);
}
