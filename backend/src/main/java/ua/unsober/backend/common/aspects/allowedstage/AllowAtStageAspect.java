package ua.unsober.backend.common.aspects.allowedstage;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import ua.unsober.backend.common.enums.EnrollmentStage;
import ua.unsober.backend.common.exceptions.EnrollmentActionNotAllowedException;
import ua.unsober.backend.common.exceptions.LocalizedEnrollmentActionNotAllowedExceptionFactory;
import ua.unsober.backend.feature.appstate.AppStateService;

@Aspect
@Component
@RequiredArgsConstructor
public class AllowAtStageAspect {
    private final LocalizedEnrollmentActionNotAllowedExceptionFactory notAllowed;
    private final AppStateService appStateService;

    @Before("@annotation(allowedAt)")
    public void before(AllowedAtStage allowedAt) throws EnrollmentActionNotAllowedException {
        EnrollmentStage currentStage = appStateService.getAppState().getEnrollmentStage();
        for(EnrollmentStage stage : allowedAt.stages()){
            if(stage.equals(currentStage))
                return;
        }
        throw notAllowed.get();
    }
}
