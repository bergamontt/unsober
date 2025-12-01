package ua.unsober.backend.feature.terminfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.unsober.backend.common.enums.Term;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TermInfoResponseDto {
    private UUID id;
    private Integer studyYear;
    private Term term;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer lenWeeks;
}
