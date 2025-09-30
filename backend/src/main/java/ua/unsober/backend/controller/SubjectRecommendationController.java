package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.SubjectRecommendationRequestDto;
import ua.unsober.backend.dtos.response.SpecialityResponseDto;
import ua.unsober.backend.dtos.response.SubjectRecommendationResponseDto;
import ua.unsober.backend.dtos.response.SubjectResponseDto;
import ua.unsober.backend.enums.Recommendation;
import ua.unsober.backend.service.SubjectRecommendationService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/subject-recommendation")
@RequiredArgsConstructor
public class SubjectRecommendationController {

    private final SubjectRecommendationService subjectRecommendationService;

    @PostMapping
    public SubjectRecommendationResponseDto create(@Valid @RequestBody SubjectRecommendationRequestDto dto) {
        return subjectRecommendationService.create(dto);
    }

    @GetMapping
    public List<SubjectRecommendationResponseDto> getAll() {
        return subjectRecommendationService.getAll();
    }

    @GetMapping("/{id}")
    public SubjectRecommendationResponseDto getById(@PathVariable UUID id) {
        return subjectRecommendationService.getById(id);
    }

    @PatchMapping("/{id}")
    public SubjectRecommendationResponseDto update(
            @PathVariable UUID id,
            @RequestBody SubjectRecommendationRequestDto dto) {
        return subjectRecommendationService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        subjectRecommendationService.delete(id);
    }

}
