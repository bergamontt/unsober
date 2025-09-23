package ua.unsober.backend.dtos.request;

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
    @NotNull(message = "{enrollmentRequest.studentId.required}")
    private UUID studentId;

    @NotNull(message = "{enrollmentRequest.courseId.required}")
    private UUID courseId;

    @NotBlank(message = "{enrollmentRequest.reason.required}")
    @Size(max = 3000, message = "{enrollmentRequest.reason.size}")
    private String reason;
}