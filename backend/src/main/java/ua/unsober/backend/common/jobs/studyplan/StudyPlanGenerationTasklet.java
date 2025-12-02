package ua.unsober.backend.common.jobs.studyplan;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import ua.unsober.backend.common.utils.studyplan.StudyPlanService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class StudyPlanGenerationTasklet implements Tasklet {

    private final StudyPlanService studyPlanService;

    @Override
    public RepeatStatus execute(
            @NonNull StepContribution contribution,
            ChunkContext chunkContext
    ) {
        JobExecution jobExecution = chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution();

        @SuppressWarnings("unchecked")
        List<UUID> studentIds = (List<UUID>) jobExecution
                .getExecutionContext()
                .get("studentIds");

        if (studentIds == null || studentIds.isEmpty()) {
            return RepeatStatus.FINISHED;
        }

        for (UUID id : studentIds) {
            try {
                studyPlanService.generateStudyPlan(id);
            } catch (Exception ex) {
                log.error("error while generating study plan for student {}", id, ex);
            }
        }

        return RepeatStatus.FINISHED;
    }

}
