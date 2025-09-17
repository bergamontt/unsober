package ua.unsober.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.unsober.backend.entities.Course;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
}
