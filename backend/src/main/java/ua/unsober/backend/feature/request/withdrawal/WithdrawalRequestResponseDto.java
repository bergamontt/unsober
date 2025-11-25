package ua.unsober.backend.feature.request.withdrawal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.unsober.backend.common.enums.RequestStatus;
import ua.unsober.backend.feature.studentenrollment.StudentEnrollmentResponseDto;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WithdrawalRequestResponseDto {
    private UUID id;
    private StudentEnrollmentResponseDto studentEnrollment;
    private String reason;
    private RequestStatus status;
    private Instant createdAt;
}