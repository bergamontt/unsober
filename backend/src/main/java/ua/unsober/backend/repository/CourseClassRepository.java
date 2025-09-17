package ua.unsober.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.unsober.backend.entities.CourseClass;

import java.util.UUID;

public interface CourseClassRepository extends JpaRepository<CourseClass, UUID> {
}
