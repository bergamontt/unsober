package ua.unsober.backend.feature.coursegroup;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseGroupRepository extends JpaRepository<CourseGroup, UUID> {
    List<CourseGroup> findByCourseId(UUID courseId);
    Optional<CourseGroup> findByCourseIdAndGroupNumber(UUID courseId, Integer groupId);
}
