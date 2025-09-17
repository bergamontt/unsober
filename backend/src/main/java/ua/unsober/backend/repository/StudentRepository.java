package ua.unsober.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.unsober.backend.entities.Student;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
}
