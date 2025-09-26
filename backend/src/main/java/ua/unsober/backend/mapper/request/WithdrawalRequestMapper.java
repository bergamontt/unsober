package ua.unsober.backend.mapper.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.dtos.request.WithdrawalRequestRequestDto;
import ua.unsober.backend.entities.WithdrawalRequest;
import ua.unsober.backend.enums.RequestStatus;
import ua.unsober.backend.exceptions.LocalizedEntityNotFoundException;
import ua.unsober.backend.repository.StudentEnrollmentRepository;

@Component
@RequiredArgsConstructor
public class WithdrawalRequestMapper {
    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final LocalizedEntityNotFoundException notFound;

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
                    notFound.forEntity("error.student-enrollment.notfound", studentEnrollmentId)));
        }
        return entity;
    }
}
