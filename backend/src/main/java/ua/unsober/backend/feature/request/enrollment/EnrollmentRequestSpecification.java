package ua.unsober.backend.feature.request.enrollment;

import org.springframework.data.jpa.domain.Specification;
import ua.unsober.backend.common.enums.RequestStatus;

public class EnrollmentRequestSpecification {

    private EnrollmentRequestSpecification() {}

    public static Specification<EnrollmentRequest> hasReasonContaining(String reason) {
        return (root, query, criteriaBuilder) -> {
            if (reason == null || reason.isBlank())
                return null;
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("reason")),
                    "%" + reason.toLowerCase() + "%"
            );
        };
    }

    public static Specification<EnrollmentRequest> hasStatus(RequestStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null)
                return null;
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<EnrollmentRequest> buildSpecification(EnrollmentRequestFilterDto filters) {
        return Specification.allOf(
                hasReasonContaining(filters.getReason()),
                hasStatus(filters.getStatus())
        );
    }
}
