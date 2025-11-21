package ua.unsober.backend.feature.studentEnrollment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, UUID> {
    List<StudentEnrollment> findAllByStudentId(UUID studentId);
    List<StudentEnrollment> findAllByCourseId(UUID courseId);
    boolean existsByStudentIdAndCourseId(UUID studentId, UUID courseId);
}
