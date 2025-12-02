package ua.unsober.backend.common.utils.studyplan.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyPlanSectionView {

    private List<StudyPlanCourseRowView> subjects;
    private StudyPlanTermLoadView autumnTotal;
    private StudyPlanTermLoadView springTotal;
    private StudyPlanTermLoadView summerTotal;

}
