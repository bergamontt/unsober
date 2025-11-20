package ua.unsober.backend.feature.subject;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.unsober.backend.common.enums.EducationLevel;
import ua.unsober.backend.common.enums.Term;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectRequestDto {
    @NotBlank(message = "{subject.name.required}")
    @Size(max = 255, message = "{subject.name.size}")
    private String name;

    @Size(max = 5000, message = "{subject.annotation.size}")
    private String annotation;

    @Size(max = 500, message = "{subject.facultyName.size}")
    private String facultyName;

    @Size(max = 500, message = "{subject.departmentName.size}")
    private String departmentName;

    @NotNull(message = "{subject.educationLevel.required}")
    private EducationLevel educationLevel;

    @NotNull(message = "{subject.credits.required}")
    @Digits(integer = 2, fraction = 1, message = "{subject.credits.digits}")
    private BigDecimal credits;

    @NotNull(message = "{subject.hoursPerWeek.required}")
    @PositiveOrZero(message = "{subject.hoursPerWeek.positive}")
    private Integer hoursPerWeek;

    @NotNull(message = "{subject.term.required}")
    private Term term;
}