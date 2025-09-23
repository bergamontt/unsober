package ua.unsober.backend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String status;
    private Integer enrollmentYear;
    private Instant createdAt;
}