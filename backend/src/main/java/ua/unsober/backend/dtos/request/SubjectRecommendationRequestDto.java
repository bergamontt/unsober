package ua.unsober.backend.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.unsober.backend.enums.Recommendation;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectRecommendationRequestDto {
    @NotNull(message = "{subjectRecommendation.subjectId.required}")
    private UUID subjectId;

    @NotNull(message = "{subjectRecommendation.specialtyId.required}")
    private UUID specialityId;

    @NotNull(message = "{subjectRecommendation.recommendation.required}")
    private Recommendation recommendation;
}