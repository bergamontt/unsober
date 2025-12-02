package ua.unsober.backend.common.utils.studyplan.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudyPlanStudentView {

    private String fullName;
    private String specialityName;
    private Integer studyYear;
    private String educationLevel;
    private String faculty;
    private BigDecimal totalCredits;

}
