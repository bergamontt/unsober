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
    @NotNull
    private UUID studentId;

    @NotNull
    private UUID courseId;

    private UUID groupId;

    @NotBlank
    @Size(max=30)
    private String status;

    @NotNull
    @Positive
    private Integer enrollmentYear;
}
