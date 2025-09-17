package ua.unsober.backend.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequestDto {
    @NotNull
    private UUID subjectId;

    @PositiveOrZero
    private Integer maxStudents;

    @NotNull
    @PositiveOrZero
    private Integer numEnrolled;

    @NotNull
    private Integer courseYear;
}