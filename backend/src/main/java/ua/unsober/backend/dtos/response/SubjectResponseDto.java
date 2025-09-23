package ua.unsober.backend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectResponseDto {
    private UUID id;
    private String name;
    private String annotation;
    private BigDecimal credits;
    private String term;
}
