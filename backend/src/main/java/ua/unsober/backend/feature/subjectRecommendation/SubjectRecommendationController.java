package ua.unsober.backend.feature.subjectRecommendation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/subject-and-speciality")
    public SubjectRecommendationResponseDto getById(
            @RequestParam UUID subjectId,
            @RequestParam UUID specialityId) {
        return subjectRecommendationService.getBySubjectAndSpecialityId(subjectId, specialityId);
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
