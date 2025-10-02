package ua.unsober.backend.feature.building;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BuildingResponseMapper {
    BuildingResponseDto toDto(Building building);
}
