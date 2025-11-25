package ua.unsober.backend.feature.coursegroup;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseGroupResponseMapper {
    CourseGroupResponseDto toDto(CourseGroup courseGroup);
}
