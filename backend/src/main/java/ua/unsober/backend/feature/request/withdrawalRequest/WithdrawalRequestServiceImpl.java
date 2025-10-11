package ua.unsober.backend.feature.request.withdrawalRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WithdrawalRequestServiceImpl implements WithdrawalRequestService {

    private final WithdrawalRequestRepository withdrawalRequestRepository;
    private final WithdrawalRequestRequestMapper requestMapper;
    private final WithdrawalRequestResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    @Override
    public WithdrawalRequestResponseDto create(WithdrawalRequestRequestDto dto) {
        return responseMapper.toDto(
                withdrawalRequestRepository.save(requestMapper.toEntity(dto)));
    }

    @Override
    public List<WithdrawalRequestResponseDto> getAll() {
        return withdrawalRequestRepository.findAll()
                .stream()
                .map(responseMapper::toDto)
                .toList();
    }

    @Override
    public WithdrawalRequestResponseDto getById(UUID id) {
        return responseMapper.toDto(
                withdrawalRequestRepository.findById(id).orElseThrow(
                        () -> notFound.get("error.withdrawal-request.notfound", id)
                )
        );
    }

    @Override
    public WithdrawalRequestResponseDto update(UUID id, WithdrawalRequestRequestDto dto) {
        WithdrawalRequest existing = withdrawalRequestRepository.findById(id).orElseThrow(() ->
                notFound.get("error.withdrawal-request.notfound", id));
        WithdrawalRequest newEntity = requestMapper.toEntity(dto);

        if (newEntity.getStudentEnrollment() != null)
            existing.setStudentEnrollment(newEntity.getStudentEnrollment());
        if (newEntity.getReason() != null)
            existing.setReason(newEntity.getReason());

        WithdrawalRequest updated = withdrawalRequestRepository.save(existing);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        if (withdrawalRequestRepository.existsById(id)) {
            withdrawalRequestRepository.deleteById(id);
        } else {
            throw notFound.get("error.withdrawal-request.notfound", id);
        }
    }
}
