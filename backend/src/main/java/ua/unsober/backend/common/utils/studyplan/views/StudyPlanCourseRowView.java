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
public class StudyPlanCourseRowView {

    private int index;
    private String subjectName;
    private BigDecimal totalHours;
    private StudyPlanTermLoadView autumn;
    private StudyPlanTermLoadView spring;
    private StudyPlanTermLoadView summer;
    private String comment;

    public String getTotalHoursLabel() {
        if (totalHours == null) return "0";
        return totalHours.stripTrailingZeros().toPlainString();
    }

}
