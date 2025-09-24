package ua.unsober.backend.mapper.response;

import org.mapstruct.Mapper;
import ua.unsober.backend.dtos.response.SubjectResponseDto;
import ua.unsober.backend.entities.Subject;

@Mapper(componentModel = "spring")
public interface SubjectResponseMapper {
    SubjectResponseDto toDto(Subject subject);
}
