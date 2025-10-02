package ua.unsober.backend.feature.courseGroup;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseGroupResponseMapper {
    CourseGroupResponseDto toDto(CourseGroup courseGroup);
}
