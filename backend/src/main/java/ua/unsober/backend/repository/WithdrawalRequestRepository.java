package ua.unsober.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.unsober.backend.entities.WithdrawalRequest;

import java.util.UUID;

public interface WithdrawalRequestRepository extends JpaRepository<WithdrawalRequest, UUID> {
}