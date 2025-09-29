package ua.unsober.backend.mapper.response;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.dtos.response.CourseClassResponseDto;
import ua.unsober.backend.entities.CourseClass;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseClassResponseMapper {
    private final CourseResponseMapper courseResponseMapper;
    private final CourseGroupResponseMapper courseGroupResponseMapper;
    private final BuildingResponseMapper buildingResponseMapper;
    private final TeacherResponseMapper teacherResponseMapper;

    public CourseClassResponseDto toDto(CourseClass courseClass){
        if(courseClass == null)
            return null;
        List<Integer> weeksList = Arrays.stream(courseClass.getWeeksList()
                .split(","))
                .map(Integer::parseInt)
                .toList();
        return CourseClassResponseDto.builder()
                .id(courseClass.getId())
                .course(courseResponseMapper.toDto(courseClass.getCourse()))
                .group(courseGroupResponseMapper.toDto(courseClass.getGroup()))
                .title(courseClass.getTitle())
                .type(courseClass.getType())
                .weeksList(weeksList)
                .weekDay(courseClass.getWeekDay())
                .classNumber(courseClass.getClassNumber())
                .location(courseClass.getLocation())
                .building(buildingResponseMapper.toDto(courseClass.getBuilding()))
                .teacher(teacherResponseMapper.toDto(courseClass.getTeacher()))
                .build();
    }
}
