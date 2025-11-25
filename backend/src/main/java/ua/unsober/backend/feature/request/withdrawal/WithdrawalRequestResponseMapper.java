package ua.unsober.backend.feature.request.withdrawal;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WithdrawalRequestResponseMapper {
    WithdrawalRequestResponseDto toDto(WithdrawalRequest withdrawalRequest);
}
