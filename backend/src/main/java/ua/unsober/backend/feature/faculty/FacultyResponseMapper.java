package ua.unsober.backend.feature.faculty;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FacultyResponseMapper {
    FacultyResponseDto toDto(Faculty faculty);
}
