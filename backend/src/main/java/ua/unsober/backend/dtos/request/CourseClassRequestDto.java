package ua.unsober.backend.dtos.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseClassRequestDto {
    @NotNull(message = "{courseClass.courseId.required}")
    private UUID courseId;

    @NotNull(message = "{courseClass.groupId.required}")
    private UUID groupId;

    @NotBlank(message = "{courseClass.title.required}")
    @Size(max = 100, message = "{courseClass.title.size}")
    private String title;

    @NotBlank(message = "{courseClass.type.required}")
    @Size(max = 100, message = "{courseClass.type.size}")
    private String type;

    @NotNull(message = "{courseClass.weeksList.required}")
    private Integer[] weeksList;

    @NotBlank(message = "{courseClass.weekDay.required}")
    @Size(max = 10, message = "{courseClass.weekDay.size}")
    private String weekDay;

    @NotNull(message = "{courseClass.classNumber.required}")
    @Min(value = 1, message = "{courseClass.classNumber.min}")
    @Max(value = 7, message = "{courseClass.classNumber.max}")
    private Integer classNumber;

    @Size(max = 10, message = "{courseClass.location.size}")
    private String location;

    private UUID teacherId;
}