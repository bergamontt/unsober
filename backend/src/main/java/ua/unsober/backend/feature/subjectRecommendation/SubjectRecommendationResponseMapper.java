package ua.unsober.backend.feature.subjectRecommendation;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectRecommendationResponseMapper {
    SubjectRecommendationResponseDto toDto(SubjectRecommendation recommendation);
}
