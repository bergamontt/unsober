package ua.unsober.backend.feature.subjectrecommendation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubjectRecommendationRepository extends JpaRepository<SubjectRecommendation, UUID> {
    Optional<SubjectRecommendation> findBySubjectIdAndSpecialityId(UUID subjectId, UUID specialityId);
}
