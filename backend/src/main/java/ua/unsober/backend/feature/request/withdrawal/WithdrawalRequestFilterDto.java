package ua.unsober.backend.feature.request.withdrawal;

import lombok.Data;
import ua.unsober.backend.common.enums.RequestStatus;

import java.util.UUID;

@Data
public class WithdrawalRequestFilterDto {
    private String reason;
    private RequestStatus status;
    private UUID studentId;
}
