package ua.unsober.backend.feature.appState;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.common.enums.EnrollmentStage;
import ua.unsober.backend.common.enums.Term;

@RestController
@RequestMapping("/app-state")
@RequiredArgsConstructor
public class AppStateController {
    private final AppStateService appStateService;

    @GetMapping
    public AppStateResponseDto getAppState() {
        return appStateService.getAppState();
    }

    @PatchMapping("/year")
    public void setCurrentYear(@RequestParam Integer year) {
        appStateService.setCurrentYear(year);
    }

    @PatchMapping("/term")
    public void setCurrentTerm(@RequestParam Term term) {
        appStateService.setCurrentTerm(term);
    }

    @PatchMapping("/stage")
    public void setCurrentStage(@RequestParam EnrollmentStage stage) {
        appStateService.setCurrentStage(stage);
    }
}
