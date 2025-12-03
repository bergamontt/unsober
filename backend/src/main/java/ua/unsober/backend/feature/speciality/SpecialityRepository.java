package ua.unsober.backend.feature.speciality;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface SpecialityRepository extends JpaRepository<Speciality, UUID>, JpaSpecificationExecutor<Speciality> {
    Optional<Speciality> findByName(String name);
    boolean existsByName(String name);
}
