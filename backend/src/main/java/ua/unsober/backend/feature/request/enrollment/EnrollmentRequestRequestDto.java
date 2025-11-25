package ua.unsober.backend.feature.request.enrollment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentRequestRequestDto {
    @NotNull(message = "{enrollment.studentId.required}")
    private UUID studentId;

    @NotNull(message = "{enrollment.courseId.required}")
    private UUID courseId;

    @NotBlank(message = "{enrollment.reason.required}")
    @Size(max = 3000, message = "{enrollment.reason.size}")
    private String reason;
}