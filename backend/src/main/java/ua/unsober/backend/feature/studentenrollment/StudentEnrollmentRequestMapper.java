package ua.unsober.backend.feature.studentenrollment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.common.enums.EnrollmentStatus;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;
import ua.unsober.backend.feature.coursegroup.CourseGroupRepository;
import ua.unsober.backend.feature.course.CourseRepository;
import ua.unsober.backend.feature.student.StudentRepository;

@Component
@RequiredArgsConstructor
public class StudentEnrollmentRequestMapper {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final CourseGroupRepository groupRepository;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

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
                    notFound.get("error.student.notfound", studentId)));
        }
        if (courseId != null) {
            entity.setCourse(courseRepository.findById(courseId).orElseThrow(() ->
                    notFound.get("error.course.notfound", courseId)));
        }
        if (groupId != null) {
            entity.setGroup(groupRepository.findById(groupId).orElseThrow(() ->
                    notFound.get("error.course-group.notfound", groupId)));
        }

        return entity;
    }

    public StudentEnrollmentRequestDto toDto(StudentEnrollment entity) {
        if (entity == null) return null;
        return StudentEnrollmentRequestDto.builder()
                .studentId(entity.getStudent() != null ? entity.getStudent().getId() : null)
                .courseId(entity.getCourse() != null ? entity.getCourse().getId() : null)
                .groupId(entity.getGroup() != null ? entity.getGroup().getId() : null)
                .enrollmentYear(entity.getEnrollmentYear())
                .build();
    }

}
