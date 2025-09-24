package ua.unsober.backend.mapper.response;

import org.mapstruct.Mapper;
import ua.unsober.backend.dtos.response.TeacherResponseDto;
import ua.unsober.backend.entities.Teacher;

@Mapper(componentModel = "spring")
public interface TeacherResponseMapper {
    TeacherResponseDto toDto(Teacher teacher);
}
