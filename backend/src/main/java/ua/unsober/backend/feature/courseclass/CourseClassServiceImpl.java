package ua.unsober.backend.feature.courseclass;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;
import ua.unsober.backend.feature.coursegroup.CourseGroupRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseClassServiceImpl implements CourseClassService {

    private final CourseClassRepository courseClassRepository;
    private final CourseGroupRepository courseGroupRepository;
    private final CourseClassRequestMapper requestMapper;
    private final CourseClassResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    private static final String CLASS_NOT_FOUND = "error.course-class.notfound";

    @Override
    public CourseClassResponseDto create(CourseClassRequestDto dto) {
        CourseClass saved = courseClassRepository.save(requestMapper.toEntity(dto));
        return responseMapper.toDto(saved);
    }

    @Override
    public List<CourseClassResponseDto> getAll() {
        return courseClassRepository.findAll().stream()
                .map(responseMapper::toDto)
                .toList();
    }

    @Override
    public CourseClassResponseDto getById(UUID id) {
        return responseMapper.toDto(
                courseClassRepository.findById(id).orElseThrow(() ->
                        notFound.get(CLASS_NOT_FOUND, id))
        );
    }

    @Override
    public List<CourseClassResponseDto> getAllByCourseId(UUID courseId) {
        return courseGroupRepository.findByCourseId(courseId)
                .stream()
                .flatMap(g -> courseClassRepository.getAllByGroupId(g.getId()).stream())
                .map(responseMapper::toDto)
                .toList();
    }

    @Override
    public CourseClassResponseDto update(UUID id, CourseClassRequestDto dto) {
        CourseClass courseClass = courseClassRepository.findById(id).orElseThrow(() ->
                notFound.get(CLASS_NOT_FOUND, id));

        CourseClass newCourseClass = requestMapper.toEntity(dto);

        if (newCourseClass.getBuilding() != null) courseClass.setBuilding(newCourseClass.getBuilding());
        if (newCourseClass.getClassNumber() != null) courseClass.setClassNumber(newCourseClass.getClassNumber());
        if (newCourseClass.getTeacher() != null) courseClass.setTeacher(newCourseClass.getTeacher());
        if (newCourseClass.getGroup() != null) courseClass.setGroup(newCourseClass.getGroup());
        if (newCourseClass.getLocation() != null) courseClass.setLocation(newCourseClass.getLocation());
        if (newCourseClass.getType() != null) courseClass.setType(newCourseClass.getType());
        if (newCourseClass.getTitle() != null) courseClass.setTitle(newCourseClass.getTitle());
        if (newCourseClass.getWeekDay() != null) courseClass.setWeekDay(newCourseClass.getWeekDay());
        if (newCourseClass.getWeeksList() != null) courseClass.setWeeksList(newCourseClass.getWeeksList());

        CourseClass updated = courseClassRepository.save(courseClass);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        if (courseClassRepository.existsById(id)) {
            courseClassRepository.deleteById(id);
        } else {
            throw notFound.get(CLASS_NOT_FOUND, id);
        }
    }
}
