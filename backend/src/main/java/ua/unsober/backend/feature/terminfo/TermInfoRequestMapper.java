package ua.unsober.backend.feature.terminfo;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TermInfoRequestMapper {
    TermInfo toEntity(TermInfoRequestDto dto);
    TermInfoRequestDto toDto(TermInfo entity);
}
