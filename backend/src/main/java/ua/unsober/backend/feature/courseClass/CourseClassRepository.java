package ua.unsober.backend.feature.courseClass;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseClassRepository extends JpaRepository<CourseClass, UUID> {
}
