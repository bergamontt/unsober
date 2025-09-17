package ua.unsober.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.unsober.backend.entities.Faculty;

import java.util.UUID;

public interface FacultyRepository extends JpaRepository<Faculty, UUID> {
}
