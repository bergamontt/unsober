package ua.unsober.backend.feature.request.enrollment;

import lombok.Data;
import ua.unsober.backend.common.enums.RequestStatus;

import java.util.UUID;

@Data
public class EnrollmentRequestFilterDto {
    private String reason;
    private RequestStatus status;
    private UUID studentId;
}
