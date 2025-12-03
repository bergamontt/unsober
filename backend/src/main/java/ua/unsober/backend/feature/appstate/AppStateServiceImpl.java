package ua.unsober.backend.feature.appstate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.enums.EnrollmentStage;
import ua.unsober.backend.common.enums.Term;
import ua.unsober.backend.feature.mail.MailService;
import ua.unsober.backend.feature.student.Student;
import ua.unsober.backend.feature.student.StudentRepository;

@Service
@RequiredArgsConstructor
public class AppStateServiceImpl implements AppStateService {
    private final AppStateRepository appStateRepository;
    private final StudentRepository studentRepository;
    private final AppStateResponseMapper responseMapper;
    private final MailService mailService;

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
        sendStageNotificationMailToAllStudents(stage);
    }

    private void sendStageNotificationMailToAllStudents(EnrollmentStage stage) {
        for (Student student : studentRepository.findAll())
            mailService.sendStageChangeNotification(student, stage);
    }
}
