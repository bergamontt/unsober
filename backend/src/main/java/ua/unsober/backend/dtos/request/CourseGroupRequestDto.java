package ua.unsober.backend.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseGroupRequestDto {
    @NotNull
    private UUID courseId;

    @NotNull
    @Min(1)
    private Integer groupNumber;

    @NotNull
    @Positive
    private Integer maxStudents;
}
