package ua.unsober.backend.feature.course;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;
import ua.unsober.backend.feature.subject.SubjectRepository;

@Component
@RequiredArgsConstructor
public class CourseRequestMapper {
    private final SubjectRepository subjectRepository;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

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
                    notFound.get("error.subject.notfound", subjectId)));
        }
        return entity;
    }
}
