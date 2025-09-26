package ua.unsober.backend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.unsober.backend.enums.EnrollmentStatus;

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