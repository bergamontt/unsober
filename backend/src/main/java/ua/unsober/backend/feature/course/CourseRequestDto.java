package ua.unsober.backend.feature.course;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseRequestDto {
    @NotNull(message = "{course.subjectId.required}")
    private UUID subjectId;

    @PositiveOrZero(message = "{course.maxStudents.non-negative}")
    private Integer maxStudents;

    @NotNull(message = "{course.courseYear.required}")
    private Integer courseYear;
}