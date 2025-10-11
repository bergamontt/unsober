package ua.unsober.backend.feature.building;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-11T17:17:46+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class BuildingResponseMapperImpl implements BuildingResponseMapper {

    @Override
    public BuildingResponseDto toDto(Building building) {
        if ( building == null ) {
            return null;
        }

        BuildingResponseDto.BuildingResponseDtoBuilder buildingResponseDto = BuildingResponseDto.builder();

        buildingResponseDto.id( building.getId() );
        buildingResponseDto.name( building.getName() );
        buildingResponseDto.address( building.getAddress() );
        buildingResponseDto.latitude( building.getLatitude() );
        buildingResponseDto.longitude( building.getLongitude() );

        return buildingResponseDto.build();
    }
}
