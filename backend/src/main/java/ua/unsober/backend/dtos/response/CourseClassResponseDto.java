package ua.unsober.backend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseClassResponseDto {
    private UUID id;
    private CourseResponseDto course;
    private CourseGroupResponseDto group;
    private String title;
    private String type;
    private List<Integer> weeksList;
    private String weekDay;
    private Integer classNumber;
    private String location;
    private BuildingResponseDto building;
    private TeacherResponseDto teacher;
}