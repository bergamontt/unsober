package ua.unsober.backend.mapper.response;

import org.mapstruct.Mapper;
import ua.unsober.backend.dtos.response.WithdrawalRequestResponseDto;
import ua.unsober.backend.entities.WithdrawalRequest;

@Mapper(componentModel = "spring")
public interface WithdrawalRequestResponseMapper {
    WithdrawalRequestResponseDto toDto(WithdrawalRequest withdrawalRequest);
}
