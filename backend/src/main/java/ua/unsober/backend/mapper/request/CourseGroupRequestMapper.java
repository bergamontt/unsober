package ua.unsober.backend.mapper.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.dtos.request.CourseGroupRequestDto;
import ua.unsober.backend.entities.CourseGroup;
import ua.unsober.backend.exceptions.LocalizedEntityNotFoundException;
import ua.unsober.backend.repository.CourseRepository;

@Component
@RequiredArgsConstructor
public class CourseGroupRequestMapper {
    private final CourseRepository courseRepository;
    private final LocalizedEntityNotFoundException notFound;

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
                    notFound.forEntity("error.course.notfound", courseId)));
        }
        return entity;
    }
}
