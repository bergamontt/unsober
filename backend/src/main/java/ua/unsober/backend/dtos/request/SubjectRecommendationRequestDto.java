package ua.unsober.backend.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectRecommendationRequestDto {
    @NotNull(message = "{subjectRecommendation.subjectId.required}")
    private UUID subjectId;

    @NotNull(message = "{subjectRecommendation.specialtyId.required}")
    private UUID specialtyId;

    @NotBlank(message = "{subjectRecommendation.recommendation.required}")
    @Size(max = 50, message = "{subjectRecommendation.recommendation.size}")
    private String recommendation;
}