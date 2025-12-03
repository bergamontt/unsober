package ua.unsober.backend.feature.building;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface BuildingRepository extends JpaRepository<Building, UUID>, JpaSpecificationExecutor<Building> {
}
