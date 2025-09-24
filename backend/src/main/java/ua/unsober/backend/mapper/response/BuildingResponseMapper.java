package ua.unsober.backend.mapper.response;

import org.mapstruct.Mapper;
import ua.unsober.backend.dtos.response.BuildingResponseDto;
import ua.unsober.backend.entities.Building;

@Mapper(componentModel = "spring")
public interface BuildingResponseMapper {
    BuildingResponseDto toDto(Building building);
}
