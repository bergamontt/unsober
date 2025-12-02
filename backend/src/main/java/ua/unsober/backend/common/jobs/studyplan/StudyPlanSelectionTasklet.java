package ua.unsober.backend.common.jobs.studyplan;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import ua.unsober.backend.feature.student.Student;
import ua.unsober.backend.feature.student.StudentRepository;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StudyPlanSelectionTasklet implements Tasklet {

    private final StudentRepository studentRepository;

    @Override
    public RepeatStatus execute(
            @NonNull StepContribution contribution,
            ChunkContext chunkContext
    ) {
        JobExecution jobExecution = chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution();

        var jobParams = jobExecution.getJobParameters();
        String studentIdParam = jobParams.getString("studentId");

        List<UUID> studentIds;
        if (studentIdParam != null) {
            UUID studentId = UUID.fromString(studentIdParam);
            studentIds = List.of(studentId);
        } else {
            studentIds = studentRepository.findAll()
                    .stream()
                    .map(Student::getId)
                    .toList();
        }

        jobExecution.getExecutionContext().put("studentIds", studentIds);
        return RepeatStatus.FINISHED;
    }

}
