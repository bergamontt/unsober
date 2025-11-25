package ua.unsober.backend.feature.request.withdrawal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public WithdrawalRequestResponseDto create(WithdrawalRequestRequestDto dto) {
        WithdrawalRequest saved = withdrawalRequestRepository.save(requestMapper.toEntity(dto));
        return responseMapper.toDto(saved);
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
        WithdrawalRequest request = withdrawalRequestRepository.findById(id)
                .orElseThrow(() -> notFound.get("error.withdrawal-request.notfound", id));
        return responseMapper.toDto(request);
    }

    @Override
    public WithdrawalRequestResponseDto update(UUID id, WithdrawalRequestRequestDto dto) {
        WithdrawalRequest existing = withdrawalRequestRepository.findById(id)
                .orElseThrow(() -> notFound.get("error.withdrawal-request.notfound", id));

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
