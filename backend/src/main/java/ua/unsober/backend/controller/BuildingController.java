package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.BuildingRequestDto;
import ua.unsober.backend.dtos.response.BuildingResponseDto;
import ua.unsober.backend.service.BuildingService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/building")
@RequiredArgsConstructor
public class BuildingController {

    private final BuildingService buildingService;

    @PostMapping
    public BuildingResponseDto create(@Valid @RequestBody BuildingRequestDto dto) {
        return buildingService.create(dto);
    }

    @GetMapping
    public List<BuildingResponseDto> getAll() {
        return buildingService.getAll();
    }

    @GetMapping("/{id}")
    public BuildingResponseDto getById(@PathVariable UUID id) {
        return buildingService.getById(id);
    }

    @PatchMapping("/{id}")
    public BuildingResponseDto update(
            @PathVariable UUID id,
            @RequestBody BuildingRequestDto dto) {
        return buildingService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        buildingService.delete(id);
    }
}

