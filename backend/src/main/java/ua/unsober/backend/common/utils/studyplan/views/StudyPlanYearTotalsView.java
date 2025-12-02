package ua.unsober.backend.common.utils.studyplan.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyPlanYearTotalsView {

    private StudyPlanTermLoadView autumn;
    private StudyPlanTermLoadView spring;
    private StudyPlanTermLoadView summer;
    private BigDecimal credits;

    public String getCreditsLabel() {
        if (credits == null) return "0";
        return credits.stripTrailingZeros().toPlainString();
    }

}
