package ua.unsober.backend.mapper.response;

import org.mapstruct.Mapper;
import ua.unsober.backend.dtos.response.AdminResponseDto;
import ua.unsober.backend.entities.Admin;

@Mapper(componentModel = "spring")
public interface AdminResponseMapper {
    AdminResponseDto toDto(Admin admin);
}
