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
}
