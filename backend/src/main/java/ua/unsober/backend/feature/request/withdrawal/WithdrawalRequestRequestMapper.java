package ua.unsober.backend.feature.request.withdrawal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.common.enums.RequestStatus;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;
import ua.unsober.backend.feature.studentenrollment.StudentEnrollmentRepository;

@Component
@RequiredArgsConstructor
public class WithdrawalRequestRequestMapper {
    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    public WithdrawalRequest toEntity(WithdrawalRequestRequestDto dto) {
        if (dto == null)
            return null;
        WithdrawalRequest entity = WithdrawalRequest.builder()
                .reason(dto.getReason())
                .status(RequestStatus.PENDING)
                .build();

        java.util.UUID studentEnrollmentId = dto.getStudentEnrollmentId();
        if (studentEnrollmentId != null) {
            entity.setStudentEnrollment(studentEnrollmentRepository.findById(studentEnrollmentId).orElseThrow(() ->
                    notFound.get("error.student-enrollment.notfound", studentEnrollmentId)));
        }
        return entity;
    }

    public WithdrawalRequestRequestDto toDto(WithdrawalRequest entity) {
        if (entity == null) return null;
        return new WithdrawalRequestRequestDto(
                entity.getStudentEnrollment() != null ? entity.getStudentEnrollment().getId() : null,
                entity.getReason()
        );
    }
}
