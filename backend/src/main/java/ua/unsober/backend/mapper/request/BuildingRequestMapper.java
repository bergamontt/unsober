package ua.unsober.backend.mapper.request;

import org.springframework.stereotype.Component;
import ua.unsober.backend.dtos.request.BuildingRequestDto;
import ua.unsober.backend.entities.Building;

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
