package ua.unsober.backend.common.utils.studyplan.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyPlanSectionsView {

    private StudyPlanSectionView mandatorySection;
    private StudyPlanSectionView profOrientedSection;
    private StudyPlanSectionView freeChoiceSection;

}
