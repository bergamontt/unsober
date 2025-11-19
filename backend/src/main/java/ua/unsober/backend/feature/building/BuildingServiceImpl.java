package ua.unsober.backend.feature.building;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public BuildingResponseDto create(BuildingRequestDto dto) {
        Building saved = buildingRepository.save(requestMapper.toEntity(dto));
        return addMap(responseMapper.toDto(saved));
    }

    @Override
    public List<BuildingResponseDto> getAll() {
        return buildingRepository.findAll()
                .stream()
                .map(responseMapper::toDto)
                .map(this::addMap)
                .toList();
    }

    @Override
    public BuildingResponseDto getById(UUID id) {
        Building building = buildingRepository.findById(id).orElseThrow(() -> {
            return notFound.get("error.building.notfound", id);
        });
        return addMap(responseMapper.toDto(building));
    }

    @Override
    public BuildingResponseDto update(UUID id, BuildingRequestDto dto) {
        Building building = buildingRepository.findById(id).orElseThrow(() -> {
            return notFound.get("error.building.notfound", id);
        });

        Building newBuilding = requestMapper.toEntity(dto);

        if (newBuilding.getName() != null) building.setName(newBuilding.getName());
        if (newBuilding.getAddress() != null) building.setAddress(newBuilding.getAddress());
        if (newBuilding.getLatitude() != null) building.setLatitude(newBuilding.getLatitude());
        if (newBuilding.getLongitude() != null) building.setLongitude(newBuilding.getLongitude());

        Building updated = buildingRepository.save(building);
        return addMap(responseMapper.toDto(updated));
    }

    @Override
    public void delete(UUID id) {
        if (buildingRepository.existsById(id)) {
            buildingRepository.deleteById(id);
        } else {
            throw notFound.get("error.building.notfound", id);
        }
    }

    private BuildingResponseDto addMap(BuildingResponseDto dto) {
        dto.setPhoto(mapService.getMap(dto.getLatitude(), dto.getLongitude()));
        return dto;
    }
}

