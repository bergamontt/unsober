package ua.unsober.backend.feature.subjectRecommendation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubjectRecommendationRepository extends JpaRepository<SubjectRecommendation, UUID> {
}
