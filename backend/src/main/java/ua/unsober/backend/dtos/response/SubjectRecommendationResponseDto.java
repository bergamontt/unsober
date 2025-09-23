package ua.unsober.backend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectRecommendationResponseDto {
    private UUID id;
    private SubjectResponseDto subject;
    private SpecialityResponseDto speciality;
    private String recommendation;
}