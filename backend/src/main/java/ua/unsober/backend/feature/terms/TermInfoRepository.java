package ua.unsober.backend.feature.terms;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.unsober.backend.common.enums.Term;

import java.util.Optional;
import java.util.UUID;

public interface TermInfoRepository extends JpaRepository<TermInfo, UUID> {
    Optional<TermInfo> getByYearAndTerm(Integer year, Term term);
}
