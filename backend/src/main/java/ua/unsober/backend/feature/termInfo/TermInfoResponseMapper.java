package ua.unsober.backend.feature.termInfo;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TermInfoResponseMapper {
    TermInfoResponseDto toDto(TermInfo termInfo);
}
