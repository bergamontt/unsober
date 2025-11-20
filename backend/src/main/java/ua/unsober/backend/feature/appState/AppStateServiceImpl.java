package ua.unsober.backend.feature.appState;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.enums.EnrollmentStage;
import ua.unsober.backend.common.enums.Term;

@Service
@RequiredArgsConstructor
public class AppStateServiceImpl implements AppStateService {
    private final AppStateRepository appStateRepository;
    private final AppStateResponseMapper responseMapper;

    @Override
    public AppStateResponseDto getAppState() {
        return responseMapper.toDto(
                appStateRepository.findTopByOrderByIdAsc()
        );
    }

    @Override
    public void setCurrentYear(Integer year) {
        AppState appState = appStateRepository.findTopByOrderByIdAsc();
        appState.setCurrentYear(year);
        appStateRepository.save(appState);
    }

    @Override
    public void setCurrentTerm(Term term) {
        AppState appState = appStateRepository.findTopByOrderByIdAsc();
        appState.setTerm(term);
        appStateRepository.save(appState);
    }

    @Override
    public void setCurrentStage(EnrollmentStage stage) {
        AppState appState = appStateRepository.findTopByOrderByIdAsc();
        appState.setEnrollmentStage(stage);
        appStateRepository.save(appState);
    }
}
