package ua.unsober.backend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseGroupResponseDto {
    private UUID id;
    private CourseResponseDto course;
    private Integer groupNumber;
    private Integer maxStudents;
    private Integer numEnrolled;
}