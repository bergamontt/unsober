package ua.unsober.backend.feature.subjectRecommendation;

import java.util.List;
import java.util.UUID;

public interface SubjectRecommendationService {
    SubjectRecommendationResponseDto create(SubjectRecommendationRequestDto dto);
    List<SubjectRecommendationResponseDto> getAll();
    SubjectRecommendationResponseDto getById(UUID id);
    SubjectRecommendationResponseDto update(UUID id, SubjectRecommendationRequestDto dto);
    void delete(UUID id);
}
