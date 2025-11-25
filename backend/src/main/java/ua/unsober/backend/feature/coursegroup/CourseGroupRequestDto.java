package ua.unsober.backend.feature.coursegroup;

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
    @NotNull(message = "{coursegroup.courseId.required}")
    private UUID courseId;

    @NotNull(message = "{coursegroup.groupNumber.required}")
    @Min(value = 1, message = "{coursegroup.groupNumber.min}")
    private Integer groupNumber;

    @NotNull(message = "{coursegroup.maxStudents.required}")
    @Positive(message = "{coursegroup.maxStudents.positive}")
    private Integer maxStudents;
}
