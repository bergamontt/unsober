package ua.unsober.backend.feature.terms;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TermInfoResponseMapper {
    TermInfoResponseDto toDto(TermInfo termInfo);
}
