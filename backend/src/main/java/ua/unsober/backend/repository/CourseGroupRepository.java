package ua.unsober.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.unsober.backend.entities.CourseGroup;

import java.util.UUID;

public interface CourseGroupRepository extends JpaRepository<CourseGroup, UUID> {
}
