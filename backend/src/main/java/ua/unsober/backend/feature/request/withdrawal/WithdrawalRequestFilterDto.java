package ua.unsober.backend.feature.request.withdrawal;

import lombok.Data;
import ua.unsober.backend.common.enums.RequestStatus;

@Data
public class WithdrawalRequestFilterDto {
    private String reason;
    private RequestStatus status;
}
