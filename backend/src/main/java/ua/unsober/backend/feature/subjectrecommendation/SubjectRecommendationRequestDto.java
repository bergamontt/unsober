package ua.unsober.backend.feature.subjectrecommendation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.unsober.backend.common.enums.Recommendation;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectRecommendationRequestDto {
    @NotNull(message = "{subjectrecommendation.subjectId.required}")
    private UUID subjectId;

    @NotNull(message = "{subjectrecommendation.specialtyId.required}")
    private UUID specialityId;

    @NotNull(message = "{subjectrecommendation.recommendation.required}")
    private Recommendation recommendation;
}