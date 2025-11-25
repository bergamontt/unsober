package ua.unsober.backend.feature.studentenrollment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentEnrollmentRequestDto {
    @NotNull(message = "{studentenrollment.studentId.required}")
    private UUID studentId;

    @NotNull(message = "{studentenrollment.courseId.required}")
    private UUID courseId;

    private UUID groupId;

    @NotNull(message = "{studentenrollment.enrollmentYear.required}")
    @Positive(message = "{studentenrollment.enrollmentYear.positive}")
    private Integer enrollmentYear;
}