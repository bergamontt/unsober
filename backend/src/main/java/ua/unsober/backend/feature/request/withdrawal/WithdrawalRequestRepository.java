package ua.unsober.backend.feature.request.withdrawal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WithdrawalRequestRepository extends JpaRepository<WithdrawalRequest, UUID> {
}