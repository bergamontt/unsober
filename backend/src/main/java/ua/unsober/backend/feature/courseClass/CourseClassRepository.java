package ua.unsober.backend.feature.courseClass;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CourseClassRepository extends JpaRepository<CourseClass, UUID> {
    List<CourseClass> getAllByCourseId(UUID courseId);
}
