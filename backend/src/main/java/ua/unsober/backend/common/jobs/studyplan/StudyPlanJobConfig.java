package ua.unsober.backend.common.jobs.studyplan;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class StudyPlanJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final StudyPlanSelectionTasklet selectionTasklet;
    private final StudyPlanGenerationTasklet generationTasklet;

    @Bean
    public Step selectStudentsForInpStep() {
        return new StepBuilder("selectStudyPlanStudentsStep", jobRepository)
                .tasklet(selectionTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step generateInpExcelStep() {
        return new StepBuilder("generateStudyPlanStep", jobRepository)
                .tasklet(generationTasklet, transactionManager)
                .build();
    }

    @Bean
    public Job inpExportJob() {
        return new JobBuilder("studyPlanJob", jobRepository)
                .start(selectStudentsForInpStep())
                .next(generateInpExcelStep())
                .build();
    }

}
