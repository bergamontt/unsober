package ua.unsober.backend.feature.teacher;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherResponseMapper {
    TeacherResponseDto toDto(Teacher teacher);
}
