package ua.unsober.backend.feature.request.withdrawal;

import org.mapstruct.Mapper;
import ua.unsober.backend.feature.student.StudentResponseMapper;

@Mapper(componentModel = "spring", uses = {StudentResponseMapper.class})
public interface WithdrawalRequestResponseMapper {
    WithdrawalRequestResponseDto toDto(WithdrawalRequest withdrawalRequest);
}
