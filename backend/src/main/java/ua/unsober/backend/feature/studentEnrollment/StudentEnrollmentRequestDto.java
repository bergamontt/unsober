package ua.unsober.backend.feature.studentEnrollment;

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
    @NotNull(message = "{studentEnrollment.studentId.required}")
    private UUID studentId;

    @NotNull(message = "{studentEnrollment.courseId.required}")
    private UUID courseId;

    private UUID groupId;

    @NotNull(message = "{studentEnrollment.enrollmentYear.required}")
    @Positive(message = "{studentEnrollment.enrollmentYear.positive}")
    private Integer enrollmentYear;
}