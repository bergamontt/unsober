package ua.unsober.backend.mapper.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.dtos.request.StudentEnrollmentRequestDto;
import ua.unsober.backend.entities.StudentEnrollment;
import ua.unsober.backend.enums.EnrollmentStatus;
import ua.unsober.backend.exceptions.LocalizedEntityNotFoundException;
import ua.unsober.backend.repository.CourseGroupRepository;
import ua.unsober.backend.repository.CourseRepository;
import ua.unsober.backend.repository.StudentRepository;

@Component
@RequiredArgsConstructor
public class StudentEnrollmentRequestMapper {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final CourseGroupRepository groupRepository;
    private final LocalizedEntityNotFoundException notFound;

    public StudentEnrollment toEntity(StudentEnrollmentRequestDto dto) {
        if (dto == null) return null;
        StudentEnrollment entity = StudentEnrollment.builder()
                .status(EnrollmentStatus.ENROLLED)
                .enrollmentYear(dto.getEnrollmentYear())
                .build();

        java.util.UUID studentId = dto.getStudentId();
        java.util.UUID courseId = dto.getCourseId();
        java.util.UUID groupId = dto.getGroupId();
        if (studentId != null) {
            entity.setStudent(studentRepository.findById(studentId).orElseThrow(() ->
                    notFound.forEntity("error.student.notfound", studentId)));
        }
        if (courseId != null) {
            entity.setCourse(courseRepository.findById(courseId).orElseThrow(() ->
                    notFound.forEntity("error.course.notfound", courseId)));
        }
        if (groupId != null) {
            entity.setGroup(groupRepository.findById(groupId).orElseThrow(() ->
                    notFound.forEntity("error.course-group.notfound", groupId)));
        }

        return entity;
    }
}
