package ua.unsober.backend.feature.request.withdrawalRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class WithdrawalRequestServiceImpl implements WithdrawalRequestService {

    private final WithdrawalRequestRepository withdrawalRequestRepository;
    private final WithdrawalRequestRequestMapper requestMapper;
    private final WithdrawalRequestResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    private static final Marker WITHDRAWAL_ACTION = MarkerFactory.getMarker("WITHDRAWAL_ACTION");
    private static final Marker WITHDRAWAL_ERROR= MarkerFactory.getMarker("WITHDRAWAL_ERROR");

    @Override
    public WithdrawalRequestResponseDto create(WithdrawalRequestRequestDto dto) {
        log.info(WITHDRAWAL_ACTION, "Creating withdrawal request...");
        WithdrawalRequest saved = withdrawalRequestRepository.save(requestMapper.toEntity(dto));
        log.info(WITHDRAWAL_ACTION, "Withdrawal request created with id={}", saved.getId());
        return responseMapper.toDto(saved);
    }

    @Override
    public List<WithdrawalRequestResponseDto> getAll() {
        log.info(WITHDRAWAL_ACTION, "Fetching all withdrawal requests...");
        List<WithdrawalRequestResponseDto> list = withdrawalRequestRepository.findAll()
                .stream()
                .map(responseMapper::toDto)
                .toList();
        log.info(WITHDRAWAL_ACTION, "Fetched {} withdrawal requests", list.size());
        return list;
    }

    @Override
    public WithdrawalRequestResponseDto getById(UUID id) {
        log.info(WITHDRAWAL_ACTION, "Fetching withdrawal request with id={}...", id);
        WithdrawalRequest request = withdrawalRequestRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn(WITHDRAWAL_ERROR, "Withdrawal request with id={} not found", id);
                    return notFound.get("error.withdrawal-request.notfound", id);
                });
        return responseMapper.toDto(request);
    }

    @Override
    public WithdrawalRequestResponseDto update(UUID id, WithdrawalRequestRequestDto dto) {
        log.info(WITHDRAWAL_ACTION, "Updating withdrawal request with id={}...", id);
        WithdrawalRequest existing = withdrawalRequestRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn(WITHDRAWAL_ERROR, "Attempt to update non-existing withdrawal request with id={}", id);
                    return notFound.get("error.withdrawal-request.notfound", id);
                });

        WithdrawalRequest newEntity = requestMapper.toEntity(dto);

        if (newEntity.getStudentEnrollment() != null)
            existing.setStudentEnrollment(newEntity.getStudentEnrollment());
        if (newEntity.getReason() != null)
            existing.setReason(newEntity.getReason());

        WithdrawalRequest updated = withdrawalRequestRepository.save(existing);
        log.info(WITHDRAWAL_ACTION, "Withdrawal request with id={} updated", updated.getId());
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        log.info(WITHDRAWAL_ACTION, "Deleting withdrawal request with id={}...", id);
        if (withdrawalRequestRepository.existsById(id)) {
            withdrawalRequestRepository.deleteById(id);
            log.info(WITHDRAWAL_ACTION, "Deleted withdrawal request with id={}", id);
        } else {
            log.warn(WITHDRAWAL_ERROR, "Attempt to delete non-existing withdrawal request with id={}", id);
            throw notFound.get("error.withdrawal-request.notfound", id);
        }
    }
}
