package ua.unsober.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.unsober.backend.entities.Speciality;

import java.util.UUID;

public interface SpecialityRepository extends JpaRepository<Speciality, UUID> {
}
