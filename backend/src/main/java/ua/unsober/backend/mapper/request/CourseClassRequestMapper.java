package ua.unsober.backend.mapper.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.dtos.request.CourseClassRequestDto;
import ua.unsober.backend.entities.CourseClass;
import ua.unsober.backend.exceptions.LocalizedEntityNotFoundException;
import ua.unsober.backend.repository.*;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourseClassRequestMapper {
    private final CourseRepository courseRepository;
    private final CourseGroupRepository groupRepository;
    private final BuildingRepository buildingRepository;
    private final TeacherRepository teacherRepository;
    private final LocalizedEntityNotFoundException notFound;

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

        UUID courseId = dto.getCourseId();
        UUID groupId = dto.getGroupId();
        UUID teacherId = dto.getTeacherId();
        UUID buildingId = dto.getBuildingId();
        if(courseId != null) {
            entity.setCourse(courseRepository.findById(courseId).orElseThrow(() ->
                    notFound.forEntity("error.course.notfound", courseId)));
        }
        if(groupId != null) {
            entity.setGroup(groupRepository.findById(groupId).orElseThrow(() ->
                    notFound.forEntity("error.course-group.notfound", groupId)));
        }
        if(buildingId != null) {
            entity.setBuilding(buildingRepository.findById(buildingId).orElseThrow(() ->
                    notFound.forEntity("error.building.notfound", buildingId)));
        }
        if(teacherId != null){
            entity.setTeacher(teacherRepository.findById(teacherId).orElseThrow(() ->
                    notFound.forEntity("error.teacher.notfound", teacherId)));
        }
        return entity;
    }
}
