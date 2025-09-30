package ua.unsober.backend.service;

import ua.unsober.backend.dtos.request.SubjectRecommendationRequestDto;
import ua.unsober.backend.dtos.response.SubjectRecommendationResponseDto;

import java.util.List;
import java.util.UUID;

public interface SubjectRecommendationService {
    SubjectRecommendationResponseDto create(SubjectRecommendationRequestDto dto);
    List<SubjectRecommendationResponseDto> getAll();
    SubjectRecommendationResponseDto getById(UUID id);
    SubjectRecommendationResponseDto update(UUID id, SubjectRecommendationRequestDto dto);
    void delete(UUID id);
}
