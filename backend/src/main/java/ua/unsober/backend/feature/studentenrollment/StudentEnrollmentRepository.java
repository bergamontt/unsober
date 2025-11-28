package ua.unsober.backend.feature.studentenrollment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, UUID> {
    List<StudentEnrollment> findAllByStudentId(UUID studentId);
    List<StudentEnrollment> findAllByStudentIdAndEnrollmentYear(UUID studentId, Integer year);
    List<StudentEnrollment> findAllByCourseId(UUID courseId);
    boolean existsByStudentIdAndCourseId(UUID studentId, UUID courseId);

    @Query("SELECT DISTINCT e.enrollmentYear " +
            "FROM StudentEnrollment e " +
            "WHERE e.student.id = :studentId " +
            "ORDER BY e.enrollmentYear")
    List<Integer> findEnrollmentYearsByStudentId(@Param("studentId") UUID studentId);
}
