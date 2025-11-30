package ua.unsober.backend.feature.courseclass;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.feature.building.BuildingRepository;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;
import ua.unsober.backend.feature.course.CourseRepository;
import ua.unsober.backend.feature.coursegroup.CourseGroupRepository;
import ua.unsober.backend.feature.teacher.TeacherRepository;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourseClassRequestMapper {
    private final CourseRepository courseRepository;
    private final CourseGroupRepository groupRepository;
    private final BuildingRepository buildingRepository;
    private final TeacherRepository teacherRepository;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    public CourseClass toEntity(CourseClassRequestDto dto){
        if(dto == null)
            return null;
        CourseClass entity = CourseClass.builder()
                .title(dto.getTitle())
                .type(dto.getType())
                .weeksList(dto.getWeeksList().stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")))
                .weekDay(dto.getWeekDay())
                .classNumber(dto.getClassNumber())
                .location(dto.getLocation())
                .build();

        UUID groupId = dto.getGroupId();
        UUID teacherId = dto.getTeacherId();
        UUID buildingId = dto.getBuildingId();
        if(groupId != null) {
            entity.setGroup(groupRepository.findById(groupId).orElseThrow(() ->
                    notFound.get("error.course-group.notfound", groupId)));
        }
        if(buildingId != null) {
            entity.setBuilding(buildingRepository.findById(buildingId).orElseThrow(() ->
                    notFound.get("error.building.notfound", buildingId)));
        }
        if(teacherId != null){
            entity.setTeacher(teacherRepository.findById(teacherId).orElseThrow(() ->
                    notFound.get("error.teacher.notfound", teacherId)));
        }
        return entity;
    }
}
