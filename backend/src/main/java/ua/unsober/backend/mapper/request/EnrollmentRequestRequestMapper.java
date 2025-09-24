package ua.unsober.backend.mapper.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.dtos.request.EnrollmentRequestRequestDto;
import ua.unsober.backend.entities.EnrollmentRequest;
import ua.unsober.backend.exceptions.LocalizedEntityNotFoundException;
import ua.unsober.backend.repository.CourseRepository;
import ua.unsober.backend.repository.StudentRepository;

@Component
@RequiredArgsConstructor
public class EnrollmentRequestRequestMapper {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final LocalizedEntityNotFoundException notFound;

    public EnrollmentRequest toEntity(EnrollmentRequestRequestDto dto) {
        if (dto == null) return null;
        EnrollmentRequest entity = EnrollmentRequest.builder()
                .reason(dto.getReason())
                .status("Processed")
                .build();

        java.util.UUID studentId = dto.getStudentId();
        java.util.UUID courseId = dto.getCourseId();
        if (studentId != null) {
            entity.setStudent(studentRepository.findById(studentId).orElseThrow(() ->
                    notFound.forEntity("error.student.notfound", studentId)));
        }
        if (courseId != null) {
            entity.setCourse(courseRepository.findById(courseId).orElseThrow(() ->
                    notFound.forEntity("error.course.notfound", courseId)));
        }
        return entity;
    }
}
