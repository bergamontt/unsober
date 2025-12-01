package ua.unsober.backend.feature.building;

import org.springframework.stereotype.Component;

@Component
public class BuildingRequestMapper {
    public Building toEntity(BuildingRequestDto dto) {
        return Building.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();
    }
    public BuildingRequestDto toDto(Building entity) {
        return BuildingRequestDto.builder()
                .name(entity.getName())
                .address(entity.getAddress())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .build();
    }
}
