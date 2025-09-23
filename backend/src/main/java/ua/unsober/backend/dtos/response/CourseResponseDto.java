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
public class CourseResponseDto {
    private UUID id;
    private SubjectResponseDto subject;
    private Integer maxStudents;
    private Integer numEnrolled;
    private Integer courseYear;
}