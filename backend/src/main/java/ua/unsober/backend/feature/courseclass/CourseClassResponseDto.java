package ua.unsober.backend.feature.courseclass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.unsober.backend.feature.building.BuildingResponseDto;
import ua.unsober.backend.feature.coursegroup.CourseGroupResponseDto;
import ua.unsober.backend.feature.course.CourseResponseDto;
import ua.unsober.backend.feature.teacher.TeacherResponseDto;
import ua.unsober.backend.common.enums.ClassType;
import ua.unsober.backend.common.enums.WeekDay;

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
    private ClassType type;
    private List<Integer> weeksList;
    private WeekDay weekDay;
    private Integer classNumber;
    private String location;
    private BuildingResponseDto building;
    private TeacherResponseDto teacher;
}