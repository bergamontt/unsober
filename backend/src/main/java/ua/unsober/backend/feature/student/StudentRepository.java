package ua.unsober.backend.feature.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ua.unsober.backend.common.enums.StudentStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID>, JpaSpecificationExecutor<Student> {
    Optional<Student> findByUserId(UUID userId);

    List<Student> findAllByStatus(StudentStatus status);
}