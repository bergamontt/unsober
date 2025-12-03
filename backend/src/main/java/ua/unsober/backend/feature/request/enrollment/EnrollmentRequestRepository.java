package ua.unsober.backend.feature.request.enrollment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ua.unsober.backend.common.enums.RequestStatus;

import java.util.List;
import java.util.UUID;

public interface EnrollmentRequestRepository extends JpaRepository<EnrollmentRequest, UUID>, JpaSpecificationExecutor<EnrollmentRequest> {
    List<EnrollmentRequest> getAllByStatus(RequestStatus status);
    List<EnrollmentRequest> getAllByStudentId(UUID studentId);
}
