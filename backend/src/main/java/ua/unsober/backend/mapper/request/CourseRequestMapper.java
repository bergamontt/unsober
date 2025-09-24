package ua.unsober.backend.mapper.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.dtos.request.CourseRequestDto;
import ua.unsober.backend.entities.Course;
import ua.unsober.backend.exceptions.LocalizedEntityNotFoundException;
import ua.unsober.backend.repository.SubjectRepository;

@Component
@RequiredArgsConstructor
public class CourseRequestMapper {
    private final SubjectRepository subjectRepository;
    private final LocalizedEntityNotFoundException notFound;

    public Course toEntity(CourseRequestDto dto) {
        if (dto == null) return null;
        Course entity = Course.builder()
                .maxStudents(dto.getMaxStudents())
                .numEnrolled(0)
                .courseYear(dto.getCourseYear())
                .build();

        java.util.UUID subjectId = dto.getSubjectId();
        if (subjectId != null) {
            entity.setSubject(subjectRepository.findById(subjectId).orElseThrow(() ->
                    notFound.forEntity("error.subject.notfound", subjectId)));
        }
        return entity;
    }
}
