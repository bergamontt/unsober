package ua.unsober.backend.feature.request.withdrawal;

import ua.unsober.backend.common.enums.RequestStatus;

import java.util.List;
import java.util.UUID;

public interface WithdrawalRequestService {
    WithdrawalRequestResponseDto create(WithdrawalRequestRequestDto dto);
    List<WithdrawalRequestResponseDto> getAll();
    List<WithdrawalRequestResponseDto> getAllWithStatus(RequestStatus status);
    WithdrawalRequestResponseDto getById(UUID id);
    WithdrawalRequestResponseDto update(UUID id, WithdrawalRequestRequestDto dto);
    WithdrawalRequestResponseDto updateStatus(UUID id, RequestStatus status);
    void delete(UUID id);
}
