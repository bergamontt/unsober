package ua.unsober.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.unsober.backend.dtos.request.BuildingRequestDto;
import ua.unsober.backend.dtos.response.BuildingResponseDto;
import ua.unsober.backend.entities.Building;
import ua.unsober.backend.exceptions.LocalizedEntityNotFoundExceptionFactory;
import ua.unsober.backend.mapper.request.BuildingRequestMapper;
import ua.unsober.backend.mapper.response.BuildingResponseMapper;
import ua.unsober.backend.repository.BuildingRepository;
import ua.unsober.backend.service.BuildingService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuildingServiceImpl implements BuildingService {

    private final BuildingRepository buildingRepository;
    private final BuildingRequestMapper requestMapper;
    private final BuildingResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    @Override
    public BuildingResponseDto create(BuildingRequestDto dto) {
        return responseMapper.toDto(
                buildingRepository.save(requestMapper.toEntity(dto))
        );
    }

    @Override
    public List<BuildingResponseDto> getAll() {
        return buildingRepository.findAll()
                .stream()
                .map(responseMapper::toDto)
                .toList();
    }

    @Override
    public BuildingResponseDto getById(UUID id) {
        return responseMapper.toDto(
                buildingRepository.findById(id)
                        .orElseThrow(() -> notFound.get("error.building.notfound", id))
        );
    }

    @Override
    public BuildingResponseDto update(UUID id, BuildingRequestDto dto) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> notFound.get("error.building.notfound", id));

        Building newBuilding = requestMapper.toEntity(dto);

        if (newBuilding.getName() != null) {
            building.setName(newBuilding.getName());
        }
        if (newBuilding.getAddress() != null) {
            building.setAddress(newBuilding.getAddress());
        }
        if (newBuilding.getLatitude() != null) {
            building.setLatitude(newBuilding.getLatitude());
        }
        if (newBuilding.getLongitude() != null) {
            building.setLongitude(newBuilding.getLongitude());
        }

        Building updated = buildingRepository.save(building);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        if (buildingRepository.existsById(id)) {
            buildingRepository.deleteById(id);
        } else {
            throw notFound.get("error.building.notfound", id);
        }
    }
}

