package ua.unsober.backend.mapper.response;

import org.mapstruct.Mapper;
import ua.unsober.backend.dtos.response.SubjectRecommendationResponseDto;
import ua.unsober.backend.entities.SubjectRecommendation;

@Mapper(componentModel = "spring")
public interface SubjectRecommendationResponseMapper {
    SubjectRecommendationResponseDto toDto(SubjectRecommendation recommendation);
}
