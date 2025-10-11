package ua.unsober.backend.feature.student;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentResponseMapper {
    StudentResponseDto toDto(Student student);
}
