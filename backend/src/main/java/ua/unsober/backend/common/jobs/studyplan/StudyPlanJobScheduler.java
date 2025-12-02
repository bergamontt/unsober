package ua.unsober.backend.common.jobs.studyplan;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StudyPlanJobScheduler {

    private final JobLauncher jobLauncher;
    private final Job studyPlanJob;

    @Scheduled(cron = "0 0 0 1 * *")
    public void runMonthly() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("runAt", System.currentTimeMillis())
                    .toJobParameters();

            JobExecution execution = jobLauncher.run(studyPlanJob, params);
            log.info("study-plan job finished with status: {}", execution.getStatus());

            if (execution.getStatus() == BatchStatus.FAILED) {
                log.error("study-plan job FAILED. jobInstanceId={}, jobExecutionId={}",
                        execution.getJobInstance().getId(), execution.getId());
            }
        } catch (Exception e) {
            log.error("failed to start study-plan job", e);
        }
    }

}
