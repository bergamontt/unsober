package ua.unsober.backend.feature.subject;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectRequestMapper {
    Subject toEntity(SubjectRequestDto dto);
}
