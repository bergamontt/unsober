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
    @NotNull
    private UUID courseId;

    @NotNull
    private UUID groupId;

    @NotBlank
    @Size(max=100)
    private String title;

    @NotBlank
    @Size(max=100)
    private String type;

    @NotNull
    private Integer[] weeksList;

    @NotBlank
    @Size(max=10)
    private String weekDay;

    @NotNull
    @Min(1)
    @Max(7)
    private Integer classNumber;

    @Size(max=10)
    private String location;

    private UUID teacherId;
}