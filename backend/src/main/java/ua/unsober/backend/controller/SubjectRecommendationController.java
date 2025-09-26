package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.SubjectRecommendationRequestDto;
import ua.unsober.backend.dtos.response.SpecialityResponseDto;
import ua.unsober.backend.dtos.response.SubjectRecommendationResponseDto;
import ua.unsober.backend.dtos.response.SubjectResponseDto;
import ua.unsober.backend.enums.Recommendation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/subject-recommendations")
public class SubjectRecommendationController {

    @PostMapping
    public SubjectRecommendationResponseDto create(@Valid @RequestBody SubjectRecommendationRequestDto dto) {
        return SubjectRecommendationResponseDto.builder()
                .id(UUID.randomUUID())
                .subject(new SubjectResponseDto())
                .speciality(new SpecialityResponseDto())
                .recommendation(dto.getRecommendation())
                .build();
    }

    @GetMapping
    public List<SubjectRecommendationResponseDto> getAll() {
        return List.of(
                SubjectRecommendationResponseDto.builder()
                        .id(UUID.randomUUID())
                        .subject(new SubjectResponseDto())
                        .speciality(new SpecialityResponseDto())
                        .recommendation(Recommendation.PROF_ORIENTED)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public SubjectRecommendationResponseDto getById(@PathVariable UUID id) {
        return SubjectRecommendationResponseDto.builder()
                .id(id)
                .subject(new SubjectResponseDto())
                .speciality(new SpecialityResponseDto())
                .recommendation(Recommendation.PROF_ORIENTED)
                .build();
    }

    @PatchMapping("/{id}")
    public SubjectRecommendationResponseDto update(
            @PathVariable UUID id,
            @RequestBody SubjectRecommendationRequestDto dto) {
        return SubjectRecommendationResponseDto.builder()
                .id(id)
                .subject(new SubjectResponseDto())
                .speciality(new SpecialityResponseDto())
                .recommendation(Optional.ofNullable(dto.getRecommendation()).orElse(Recommendation.PROF_ORIENTED))
                .build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {}

}
