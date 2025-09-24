package ua.unsober.backend.mapper.response;

import org.mapstruct.Mapper;
import ua.unsober.backend.dtos.response.CourseGroupResponseDto;
import ua.unsober.backend.entities.CourseGroup;

@Mapper(componentModel = "spring")
public interface CourseGroupResponseMapper {
    CourseGroupResponseDto toDto(CourseGroup courseGroup);
}
