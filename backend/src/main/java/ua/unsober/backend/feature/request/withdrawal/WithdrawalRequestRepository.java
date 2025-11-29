package ua.unsober.backend.feature.request.withdrawal;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.unsober.backend.common.enums.RequestStatus;

import java.util.List;
import java.util.UUID;

public interface WithdrawalRequestRepository extends JpaRepository<WithdrawalRequest, UUID> {
    List<WithdrawalRequest> getAllByStatus(RequestStatus status);
}