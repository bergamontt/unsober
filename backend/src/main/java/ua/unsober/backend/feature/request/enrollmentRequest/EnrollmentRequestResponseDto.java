package ua.unsober.backend.feature.request.enrollmentRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.unsober.backend.feature.student.StudentResponseDto;
import ua.unsober.backend.common.enums.RequestStatus;
import ua.unsober.backend.feature.course.CourseResponseDto;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnrollmentRequestResponseDto {
    private UUID id;
    private StudentResponseDto student;
    private CourseResponseDto course;
    private String reason;
    private RequestStatus status;
    private Instant createdAt;
}