package ua.unsober.backend.feature.subject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.unsober.backend.common.enums.EducationLevel;
import ua.unsober.backend.common.enums.Term;

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
    private String facultyName;
    private String departmentName;
    private EducationLevel educationLevel;
    private BigDecimal credits;
    private Integer hoursPerWeek;
    private Term term;
}