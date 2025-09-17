package ua.unsober.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.unsober.backend.entities.StudentEnrollment;

import java.util.UUID;

public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, UUID> {
}
