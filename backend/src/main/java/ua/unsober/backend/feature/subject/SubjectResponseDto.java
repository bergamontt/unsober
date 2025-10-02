package ua.unsober.backend.feature.subject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectResponseDto {
    private UUID id;
    private String name;
    private String annotation;
    private BigDecimal credits;
    private String term;
}
