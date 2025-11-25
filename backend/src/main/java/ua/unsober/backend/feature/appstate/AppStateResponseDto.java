package ua.unsober.backend.feature.appstate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.unsober.backend.common.enums.EnrollmentStage;
import ua.unsober.backend.common.enums.Term;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppStateResponseDto {
    private Integer currentYear;
    private Term term;
    private EnrollmentStage enrollmentStage;
    private Instant updateTime;
}
