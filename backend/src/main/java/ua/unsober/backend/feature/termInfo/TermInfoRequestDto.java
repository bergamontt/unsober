package ua.unsober.backend.feature.termInfo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.unsober.backend.common.enums.Term;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TermInfoRequestDto {
    @NotNull(message = "{term-info.year.required}")
    private Integer year;

    @NotNull(message = "{term-info.term.required}")
    private Term term;

    @NotNull(message = "{term-info.start-date.required}")
    private LocalDate startDate;

    @NotNull(message = "{term-info.end-date.required}")
    private LocalDate endDate;

    @NotNull(message = "{term-info.len-weeks.required}")
    @Positive(message = "{term-info.len-weeks.positive}")
    private Integer lenWeeks;
}