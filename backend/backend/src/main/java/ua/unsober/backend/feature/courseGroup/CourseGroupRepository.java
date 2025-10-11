package ua.unsober.backend.feature.courseGroup;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseGroupRepository extends JpaRepository<CourseGroup, UUID> {
}
