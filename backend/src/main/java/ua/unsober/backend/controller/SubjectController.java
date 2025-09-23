package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.SubjectRequestDto;
import ua.unsober.backend.dtos.response.SubjectResponseDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    @PostMapping
    public SubjectResponseDto create(@Valid @RequestBody SubjectRequestDto dto) {
        return SubjectResponseDto.builder()
                .id(UUID.randomUUID())
                .name(dto.getName())
                .annotation(dto.getAnnotation())
                .credits(dto.getCredits())
                .term(dto.getTerm())
                .build();
    }

    @GetMapping
    public List<SubjectResponseDto> getAll() {
        return List.of(
                SubjectResponseDto.builder()
                        .id(UUID.randomUUID())
                        .name("Математика")
                        .annotation("Курс з вищої математики")
                        .credits(new BigDecimal("4.0"))
                        .term("Fall")
                        .build()
        );
    }

    @GetMapping("/{id}")
    public SubjectResponseDto getById(@PathVariable UUID id) {
        return SubjectResponseDto.builder()
                .id(id)
                .name("Математика")
                .annotation("Курс з вищої математики")
                .credits(new BigDecimal("4.0"))
                .term("Fall")
                .build();
    }

    @PatchMapping("/{id}")
    public SubjectResponseDto update(
            @PathVariable UUID id,
            @RequestBody SubjectRequestDto dto) {
        return SubjectResponseDto.builder()
                .id(id)
                .name(Optional.ofNullable(dto.getName()).orElse("Математика"))
                .annotation(Optional.ofNullable(dto.getAnnotation()).orElse("Курс з вищої математики"))
                .credits(Optional.ofNullable(dto.getCredits()).orElse(new BigDecimal("4.0")))
                .term(Optional.ofNullable(dto.getTerm()).orElse("Fall"))
                .build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {}

}
