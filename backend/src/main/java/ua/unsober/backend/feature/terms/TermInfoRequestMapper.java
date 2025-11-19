package ua.unsober.backend.feature.terms;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TermInfoRequestMapper {
    TermInfo toEntity(TermInfoRequestDto dto);
}
