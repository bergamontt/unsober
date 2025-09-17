package ua.unsober.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentEnrollment extends JpaRepository<StudentEnrollment, UUID> {
}
