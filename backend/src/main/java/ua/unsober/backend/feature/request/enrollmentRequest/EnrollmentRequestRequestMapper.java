package ua.unsober.backend.feature.request.enrollmentRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.common.enums.RequestStatus;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;
import ua.unsober.backend.feature.course.CourseRepository;
import ua.unsober.backend.feature.student.StudentRepository;

@Component
@RequiredArgsConstructor
public class EnrollmentRequestRequestMapper {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    public EnrollmentRequest toEntity(EnrollmentRequestRequestDto dto) {
        if (dto == null) return null;
        EnrollmentRequest entity = EnrollmentRequest.builder()
                .reason(dto.getReason())
                .status(RequestStatus.PENDING)
                .build();

        java.util.UUID studentId = dto.getStudentId();
        java.util.UUID courseId = dto.getCourseId();
        if (studentId != null) {
            entity.setStudent(studentRepository.findById(studentId).orElseThrow(() ->
                    notFound.get("error.student.notfound", studentId)));
        }
        if (courseId != null) {
            entity.setCourse(courseRepository.findById(courseId).orElseThrow(() ->
                    notFound.get("error.course.notfound", courseId)));
        }
        return entity;
    }
}
