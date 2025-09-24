package ua.unsober.backend.mapper.response;

import org.mapstruct.Mapper;
import ua.unsober.backend.dtos.response.StudentResponseDto;
import ua.unsober.backend.entities.Student;

@Mapper(componentModel = "spring")
public interface StudentResponseMapper {
    StudentResponseDto toDto(Student student);
}
