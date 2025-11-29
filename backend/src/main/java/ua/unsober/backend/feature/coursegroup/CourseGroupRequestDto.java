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
    @NotNull(message = "{courseGroup.courseId.required}")
    private UUID courseId;

    @NotNull(message = "{courseGroup.groupNumber.required}")
    @Min(value = 1, message = "{courseGroup.groupNumber.min}")
    private Integer groupNumber;

    @NotNull(message = "{courseGroup.maxStudents.required}")
    @Positive(message = "{courseGroup.maxStudents.positive}")
    private Integer maxStudents;
}
