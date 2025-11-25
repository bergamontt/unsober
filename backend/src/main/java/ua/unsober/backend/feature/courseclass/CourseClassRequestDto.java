package ua.unsober.backend.feature.courseclass;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.unsober.backend.common.enums.ClassType;
import ua.unsober.backend.common.enums.WeekDay;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseClassRequestDto {
    @NotNull(message = "{courseclass.courseId.required}")
    private UUID courseId;

    @NotNull(message = "{courseclass.groupId.required}")
    private UUID groupId;

    @NotBlank(message = "{courseclass.title.required}")
    @Size(max = 100, message = "{courseclass.title.size}")
    private String title;

    @NotNull(message = "{courseclass.type.required}")
    private ClassType type;

    @NotNull(message = "{courseclass.weeksList.required}")
    private List<Integer> weeksList;

    @NotNull(message = "{courseclass.weekDay.required}")
    private WeekDay weekDay;

    @NotNull(message = "{courseclass.classNumber.required}")
    @Min(value = 1, message = "{courseclass.classNumber.min}")
    @Max(value = 7, message = "{courseclass.classNumber.max}")
    private Integer classNumber;

    @Size(max = 100, message = "{courseclass.location.size}")
    private String location;

    private UUID buildingId;

    private UUID teacherId;
}