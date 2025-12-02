package ua.unsober.backend.feature.studyplan;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/study-plan")
@RequiredArgsConstructor
public class StudyPlanController {

    private final JobLauncher jobLauncher;
    private final Job studyPlanJob;

    @SneakyThrows
    @PostMapping("/{id}/generate")
    public void generate(@PathVariable String id) {
        JobParameters params = new JobParametersBuilder()
                .addString("studentId", id)
                .addLong("runAt", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(studyPlanJob, params);
    }

}
