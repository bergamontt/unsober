package ua.unsober.backend.feature.termInfo;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TermInfoRequestMapper {
    TermInfo toEntity(TermInfoRequestDto dto);
}
