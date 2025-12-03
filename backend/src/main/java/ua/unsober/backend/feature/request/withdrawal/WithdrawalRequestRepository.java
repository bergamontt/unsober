package ua.unsober.backend.feature.request.withdrawal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.unsober.backend.common.enums.RequestStatus;

import java.util.List;
import java.util.UUID;

public interface WithdrawalRequestRepository extends JpaRepository<WithdrawalRequest, UUID>, JpaSpecificationExecutor<WithdrawalRequest> {
    List<WithdrawalRequest> getAllByStatus(RequestStatus status);
    @Query("select w from WithdrawalRequest w " +
            "where w.studentEnrollment.student.id = :studentId")
    List<WithdrawalRequest> getAllByStudentId(@Param("studentId") UUID studentId);
}