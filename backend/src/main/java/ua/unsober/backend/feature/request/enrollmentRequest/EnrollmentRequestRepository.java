package ua.unsober.backend.feature.request.enrollmentRequest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnrollmentRequestRepository extends JpaRepository<EnrollmentRequest, UUID> {
}
