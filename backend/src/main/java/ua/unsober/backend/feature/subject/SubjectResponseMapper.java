package ua.unsober.backend.feature.subject;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectResponseMapper {
    SubjectResponseDto toDto(Subject subject);
}
