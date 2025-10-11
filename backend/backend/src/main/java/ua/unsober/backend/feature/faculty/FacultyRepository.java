package ua.unsober.backend.feature.faculty;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FacultyRepository extends JpaRepository<Faculty, UUID> {
}
