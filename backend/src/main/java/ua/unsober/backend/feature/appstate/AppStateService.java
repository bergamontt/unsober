package ua.unsober.backend.feature.appstate;

import ua.unsober.backend.common.enums.EnrollmentStage;
import ua.unsober.backend.common.enums.Term;

public interface AppStateService {
    AppStateResponseDto getAppState();
    void setCurrentYear(Integer year);
    void setCurrentTerm(Term term);
    void setCurrentStage(EnrollmentStage stage);
}
