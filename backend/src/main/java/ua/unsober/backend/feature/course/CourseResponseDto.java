package ua.unsober.backend.feature.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.unsober.backend.feature.subject.SubjectResponseDto;

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