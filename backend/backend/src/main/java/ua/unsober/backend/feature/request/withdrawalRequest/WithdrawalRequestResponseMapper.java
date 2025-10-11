package ua.unsober.backend.feature.request.withdrawalRequest;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WithdrawalRequestResponseMapper {
    WithdrawalRequestResponseDto toDto(WithdrawalRequest withdrawalRequest);
}
