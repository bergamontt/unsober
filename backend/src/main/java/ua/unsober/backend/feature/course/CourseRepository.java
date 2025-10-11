package ua.unsober.backend.feature.course;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    Optional<Course> findBySubjectIdAndCourseYear(UUID subjectId, Integer courseYear);
}
