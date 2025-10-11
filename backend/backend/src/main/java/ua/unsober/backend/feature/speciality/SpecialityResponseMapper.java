package ua.unsober.backend.feature.speciality;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpecialityResponseMapper {
    SpecialityResponseDto toDto(Speciality speciality);
}
