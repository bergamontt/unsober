package ua.unsober.backend.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentEnrollmentRequestDto {
    @NotNull(message = "{studentEnrollment.studentId.required}")
    private UUID studentId;

    @NotNull(message = "{studentEnrollment.courseId.required}")
    private UUID courseId;

    private UUID groupId;

    @NotBlank(message = "{studentEnrollment.status.required}")
    @Size(max = 30, message = "{studentEnrollment.status.size}")
    private String status;

    @NotNull(message = "{studentEnrollment.enrollmentYear.required}")
    @Positive(message = "{studentEnrollment.enrollmentYear.positive}")
    private Integer enrollmentYear;
}