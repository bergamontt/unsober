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
public class StudyPlanTermLoadView {

    private Integer hoursPerWeek;
    private BigDecimal credits;

    public String getLabel() {
        int hpw = hoursPerWeek != null ? hoursPerWeek : 0;
        BigDecimal cr = credits != null ? credits : BigDecimal.ZERO;

        if (hpw == 0 && cr.compareTo(BigDecimal.ZERO) == 0) return "";

        return hpw + "/" + cr.stripTrailingZeros().toPlainString();
    }

    public static StudyPlanTermLoadView empty() {
        return StudyPlanTermLoadView.builder()
                .hoursPerWeek(0)
                .credits(BigDecimal.ZERO)
                .build();
    }

    public static StudyPlanTermLoadView of(Integer hoursPerWeek, BigDecimal credits) {
        return StudyPlanTermLoadView.builder()
                .hoursPerWeek(hoursPerWeek != null ? hoursPerWeek : 0)
                .credits(credits != null ? credits : BigDecimal.ZERO)
                .build();
    }

}
