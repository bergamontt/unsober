package ua.unsober.backend.feature.studentenrollment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.unsober.backend.common.enums.EnrollmentStatus;
import ua.unsober.backend.feature.course.CourseResponseDto;
import ua.unsober.backend.feature.coursegroup.CourseGroupResponseDto;
import ua.unsober.backend.feature.student.StudentResponseDto;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentEnrollmentResponseDto {
    private UUID id;
    private StudentResponseDto student;
    private CourseResponseDto course;
    private CourseGroupResponseDto courseGroup;
    private EnrollmentStatus status;
    private Integer enrollmentYear;
    private Instant createdAt;
}