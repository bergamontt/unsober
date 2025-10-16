package ua.unsober.backend.feature.building;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;
import ua.unsober.unsoberstartermap.MapService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuildingServiceImpl implements BuildingService {

    private final BuildingRepository buildingRepository;
    private final BuildingRequestMapper requestMapper;
    private final BuildingResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;
    private final MapService mapService;

    private static final Marker BUILDING_ACTION = MarkerFactory.getMarker("BUILDING_ACTION");
    private static final Marker BUILDING_ERROR = MarkerFactory.getMarker("BUILDING_ERROR");

    @Override
    public BuildingResponseDto create(BuildingRequestDto dto) {
        log.info(BUILDING_ACTION, "Creating new building...");
        Building saved = buildingRepository.save(requestMapper.toEntity(dto));
        log.info(BUILDING_ACTION, "Building created with id={}", saved.getId());
        return addMap(responseMapper.toDto(saved));
    }

    @Override
    public List<BuildingResponseDto> getAll() {
        log.info(BUILDING_ACTION, "Fetching all buildings...");
        List<BuildingResponseDto> result = buildingRepository.findAll()
                .stream()
                .map(responseMapper::toDto)
                .map(this::addMap)
                .toList();
        log.info(BUILDING_ACTION, "Fetched {} buildings", result.size());
        return result;
    }

    @Override
    public BuildingResponseDto getById(UUID id) {
        log.info(BUILDING_ACTION, "Fetching building with id={}...", id);
        Building building = buildingRepository.findById(id).orElseThrow(() -> {
            log.warn(BUILDING_ERROR, "Attempt to fetch non-existing building with id={}", id);
            return notFound.get("error.building.notfound", id);
        });
        return addMap(responseMapper.toDto(building));
    }

    @Override
    public BuildingResponseDto update(UUID id, BuildingRequestDto dto) {
        log.info(BUILDING_ACTION, "Updating building with id={}...", id);
        Building building = buildingRepository.findById(id).orElseThrow(() -> {
            log.warn(BUILDING_ERROR, "Attempt to update non-existing building with id={}", id);
            return notFound.get("error.building.notfound", id);
        });

        Building newBuilding = requestMapper.toEntity(dto);

        if (newBuilding.getName() != null) building.setName(newBuilding.getName());
        if (newBuilding.getAddress() != null) building.setAddress(newBuilding.getAddress());
        if (newBuilding.getLatitude() != null) building.setLatitude(newBuilding.getLatitude());
        if (newBuilding.getLongitude() != null) building.setLongitude(newBuilding.getLongitude());

        Building updated = buildingRepository.save(building);
        log.info(BUILDING_ACTION, "Successfully updated building with id={}", id);
        return addMap(responseMapper.toDto(updated));
    }

    @Override
    public void delete(UUID id) {
        log.info(BUILDING_ACTION, "Deleting building with id={}...", id);
        if (buildingRepository.existsById(id)) {
            buildingRepository.deleteById(id);
            log.info(BUILDING_ACTION, "Building with id={} deleted", id);
        } else {
            log.warn(BUILDING_ERROR, "Attempt to delete non-existing building with id={}", id);
            throw notFound.get("error.building.notfound", id);
        }
    }

    private BuildingResponseDto addMap(BuildingResponseDto dto) {
        dto.setPhoto(mapService.getMap(dto.getLatitude(), dto.getLongitude()));
        return dto;
    }
}

