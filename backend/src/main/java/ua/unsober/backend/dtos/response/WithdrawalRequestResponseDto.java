package ua.unsober.backend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawalRequestResponseDto {
    private UUID id;
    private StudentEnrollmentResponseDto studentEnrollment;
    private String reason;
    private String status;
    private Instant createdAt;
}