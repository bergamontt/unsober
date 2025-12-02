package ua.unsober.backend.common.utils.studyplan;

import java.io.IOException;
import java.util.UUID;

public interface StudyPlanService {

    void generateStudyPlan(UUID id) throws IOException;

}
