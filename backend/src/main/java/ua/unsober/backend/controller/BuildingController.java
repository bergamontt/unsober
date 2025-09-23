package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.BuildingRequestDto;
import ua.unsober.backend.dtos.response.BuildingResponseDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/buildings")
public class BuildingController {

    @PostMapping
    public BuildingResponseDto create(@Valid @RequestBody BuildingRequestDto dto) {
        return BuildingResponseDto.builder()
                .id(UUID.randomUUID())
                .name(dto.getName())
                .address(dto.getAddress())
                .longitude(dto.getLongitude())
                .latitude(dto.getLatitude())
                .build();
    }

    @GetMapping
    public List<BuildingResponseDto> getAll() {
        return List.of(
                BuildingResponseDto.builder()
                        .id(UUID.randomUUID())
                        .name("1 корпус")
                        .address("вулиця Григорія Сковороди, 2, Київ, 04655")
                        .longitude(new BigDecimal("50.46424999685912"))
                        .latitude(new BigDecimal("30.520296001220466"))
                        .build()
        );
    }

    @GetMapping("/{id}")
    public BuildingResponseDto getById(@PathVariable UUID id) {
        return BuildingResponseDto.builder()
                .id(id)
                .name("1 корпус")
                .address("вулиця Григорія Сковороди, 2, Київ, 04655")
                .longitude(new BigDecimal("50.46424999685912"))
                .latitude(new BigDecimal("30.520296001220466"))
                .build();
    }

    @PatchMapping("/{id}")
    public BuildingResponseDto update(
            @PathVariable UUID id,
            @RequestBody BuildingRequestDto dto) {
        return BuildingResponseDto.builder()
                .id(id)
                .name(dto.getName() == null ? "1 корпус" : dto.getName())
                .address(dto.getAddress() == null ? "вулиця Григорія Сковороди, 2, Київ, 04655" : dto.getAddress())
                .longitude(dto.getLongitude() == null ? new BigDecimal("50.46424999685912")  : dto.getLongitude())
                .latitude(dto.getLatitude() == null ? new BigDecimal("30.520296001220466") : dto.getLatitude())
                .build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
    }
}
