package ua.unsober.backend.feature.admin;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminResponseMapper {
    AdminResponseDto toDto(Admin admin);
}
