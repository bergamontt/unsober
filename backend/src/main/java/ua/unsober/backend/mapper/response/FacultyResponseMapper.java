package ua.unsober.backend.mapper.response;

import org.mapstruct.Mapper;
import ua.unsober.backend.dtos.response.FacultyResponseDto;
import ua.unsober.backend.entities.Faculty;

@Mapper(componentModel = "spring")
public interface FacultyResponseMapper {
    FacultyResponseDto toDto(Faculty faculty);
}
