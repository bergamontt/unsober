package ua.unsober.backend.feature.subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, UUID>, JpaSpecificationExecutor<Subject> {
    List<Subject> findByName(String name);
    @Query("""
      SELECT s
      FROM Subject s
      JOIN SubjectRecommendation sr ON sr.subject = s
      WHERE s.name = :subjectName
        AND sr.speciality.name = :specialityName
    """)
    Optional<Subject> findByNameAndSpecialityName(@Param("subjectName") String subjectName,
                                                  @Param("specialityName") String specialityName);
}