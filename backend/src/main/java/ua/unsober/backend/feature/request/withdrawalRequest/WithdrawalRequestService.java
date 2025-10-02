package ua.unsober.backend.feature.request.withdrawalRequest;

import java.util.List;
import java.util.UUID;

public interface WithdrawalRequestService {
    WithdrawalRequestResponseDto create(WithdrawalRequestRequestDto dto);
    List<WithdrawalRequestResponseDto> getAll();
    WithdrawalRequestResponseDto getById(UUID id);
    WithdrawalRequestResponseDto update(UUID id, WithdrawalRequestRequestDto dto);
    void delete(UUID id);
}
