package ua.unsober.backend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.unsober.backend.enums.Recommendation;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectRecommendationResponseDto {
    private UUID id;
    private SubjectResponseDto subject;
    private SpecialityResponseDto speciality;
    private Recommendation recommendation;
}