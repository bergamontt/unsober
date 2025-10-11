package ua.unsober.backend.feature.course;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseResponseMapper {
    CourseResponseDto toDto(Course course);
}
