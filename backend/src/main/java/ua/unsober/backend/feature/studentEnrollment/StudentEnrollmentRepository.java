package ua.unsober.backend.feature.studentEnrollment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, UUID> {
}
