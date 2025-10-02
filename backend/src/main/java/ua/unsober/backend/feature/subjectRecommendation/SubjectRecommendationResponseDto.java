package ua.unsober.backend.feature.subjectRecommendation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.unsober.backend.common.enums.Recommendation;
import ua.unsober.backend.feature.speciality.SpecialityResponseDto;
import ua.unsober.backend.feature.subject.SubjectResponseDto;

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