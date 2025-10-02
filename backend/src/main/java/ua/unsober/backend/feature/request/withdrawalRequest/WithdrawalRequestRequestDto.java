package ua.unsober.backend.feature.request.withdrawalRequest;

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
public class WithdrawalRequestRequestDto {
    @NotNull(message = "{withdrawal.studentEnrollmentId.required}")
    private UUID studentEnrollmentId;

    @NotBlank(message = "{withdrawal.reason.required}")
    @Size(max = 3000, message = "{withdrawal.reason.size}")
    private String reason;
}