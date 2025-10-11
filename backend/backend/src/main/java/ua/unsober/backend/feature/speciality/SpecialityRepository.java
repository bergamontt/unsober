package ua.unsober.backend.feature.speciality;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpecialityRepository extends JpaRepository<Speciality, UUID> {
}
