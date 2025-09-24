package ua.unsober.backend.mapper.response;

import org.mapstruct.Mapper;
import ua.unsober.backend.dtos.response.CourseResponseDto;
import ua.unsober.backend.entities.Course;

@Mapper(componentModel = "spring")
public interface CourseResponseMapper {
    CourseResponseDto toDto(Course course);
}
