package ua.unsober.backend.feature.coursegroup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;
import ua.unsober.backend.feature.course.CourseRepository;

@Component
@RequiredArgsConstructor
public class CourseGroupRequestMapper {
    private final CourseRepository courseRepository;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    public CourseGroup toEntity(CourseGroupRequestDto dto) {
        if (dto == null) return null;
        CourseGroup entity = CourseGroup.builder()
                .groupNumber(dto.getGroupNumber())
                .maxStudents(dto.getMaxStudents())
                .numEnrolled(0)
                .build();

        java.util.UUID courseId = dto.getCourseId();
        if (courseId != null) {
            entity.setCourse(courseRepository.findById(courseId).orElseThrow(() ->
                    notFound.get("error.course.notfound", courseId)));
        }
        return entity;
    }

    public CourseGroupRequestDto toDto(CourseGroup entity) {
        if (entity == null) return null;
        return CourseGroupRequestDto.builder()
                .courseId(entity.getCourse() != null ? entity.getCourse().getId() : null)
                .groupNumber(entity.getGroupNumber())
                .maxStudents(entity.getMaxStudents())
                .build();
    }
}
