package ua.unsober.backend.feature.course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID>, JpaSpecificationExecutor<Course> {
    Optional<Course> findBySubjectIdAndCourseYear(UUID subjectId, Integer courseYear);
    Page<Course> findAllByCourseYear(Integer courseYear, Pageable pageable);
}
