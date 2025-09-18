package ua.unsober.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.unsober.backend.entities.Building;

import java.util.UUID;

public interface BuildingRepository extends JpaRepository<Building, UUID> {
}
