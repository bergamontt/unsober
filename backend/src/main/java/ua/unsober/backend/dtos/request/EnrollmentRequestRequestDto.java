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
    @NotNull
    private UUID studentId;

    @NotNull
    private UUID courseId;

    @NotBlank
    @Size(max=3000)
    private String reason;

    @NotBlank
    @Size(max=20)
    private String status;
}
