package ua.unsober.backend.feature.request.withdrawal;

import org.springframework.data.jpa.domain.Specification;
import ua.unsober.backend.common.enums.RequestStatus;

import java.util.UUID;

public class WithdrawalRequestSpecification {

    private WithdrawalRequestSpecification() {}
    
    public static Specification<WithdrawalRequest> hasReasonContaining(String reason) {
        return (root, query, criteriaBuilder) -> {
            if (reason == null || reason.isBlank()) {
                return null;
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("reason")),
                    "%" + reason.toLowerCase() + "%"
            );
        };
    }

    public static Specification<WithdrawalRequest> hasStatus(RequestStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<WithdrawalRequest> hasStudentId(UUID studentId) {
        return (root, query, criteriaBuilder) -> {
            if (studentId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("studentEnrollment").get("student").get("id"), studentId);
        };
    }

    public static Specification<WithdrawalRequest> buildSpecification(WithdrawalRequestFilterDto filters) {
        return Specification.allOf(
                hasReasonContaining(filters.getReason()),
                hasStatus(filters.getStatus()),
                hasStudentId(filters.getStudentId())
        );
    }
}
