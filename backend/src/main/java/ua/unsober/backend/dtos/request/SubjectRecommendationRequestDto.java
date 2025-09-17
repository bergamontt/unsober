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
    @NotNull
    private UUID subjectId;

    @NotNull
    private UUID specialtyId;

    @NotBlank
    @Size(max=50)
    private String recommendation;
}