package ua.unsober.backend.feature.courseClass;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseClassServiceImpl implements CourseClassService {

    private final CourseClassRepository courseClassRepository;
    private final CourseClassRequestMapper requestMapper;
    private final CourseClassResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    private static final Marker COURSE_CLASS_ACTION = MarkerFactory.getMarker("COURSE_CLASS_ACTION");
    private static final Marker COURSE_CLASS_ERROR = MarkerFactory.getMarker("COURSE_CLASS_ERROR");

    @Override
    public CourseClassResponseDto create(CourseClassRequestDto dto) {
        log.info(COURSE_CLASS_ACTION, "Creating new course class...");
        CourseClass saved = courseClassRepository.save(requestMapper.toEntity(dto));
        log.info(COURSE_CLASS_ACTION, "Course class created with id={}", saved.getId());
        return responseMapper.toDto(saved);
    }

    @Override
    public List<CourseClassResponseDto> getAll() {
        log.info(COURSE_CLASS_ACTION, "Fetching all course classes...");
        List<CourseClassResponseDto> result = courseClassRepository.findAll().stream()
                .map(responseMapper::toDto)
                .toList();
        log.info(COURSE_CLASS_ACTION, "Fetched {} course classes", result.size());
        return result;
    }

    @Override
    public CourseClassResponseDto getById(UUID id) {
        log.info(COURSE_CLASS_ACTION, "Fetching course class with id={}...", id);
        return responseMapper.toDto(
                courseClassRepository.findById(id).orElseThrow(() -> {
                    log.warn(COURSE_CLASS_ERROR, "Attempt to fetch non-existing course class with id={}", id);
                    return notFound.get("error.course-class.notfound", id);
                })
        );
    }

    @Override
    public CourseClassResponseDto update(UUID id, CourseClassRequestDto dto) {
        log.info(COURSE_CLASS_ACTION, "Updating course class with id={}...", id);
        CourseClass courseClass = courseClassRepository.findById(id).orElseThrow(() -> {
            log.warn(COURSE_CLASS_ERROR, "Attempt to update non-existing course class with id={}", id);
            return notFound.get("error.course-class.notfound", id);
        });

        CourseClass newCourseClass = requestMapper.toEntity(dto);

        if (newCourseClass.getBuilding() != null) courseClass.setBuilding(newCourseClass.getBuilding());
        if (newCourseClass.getClassNumber() != null) courseClass.setClassNumber(newCourseClass.getClassNumber());
        if (newCourseClass.getTeacher() != null) courseClass.setTeacher(newCourseClass.getTeacher());
        if (newCourseClass.getCourse() != null) courseClass.setCourse(newCourseClass.getCourse());
        if (newCourseClass.getGroup() != null) courseClass.setGroup(newCourseClass.getGroup());
        if (newCourseClass.getLocation() != null) courseClass.setLocation(newCourseClass.getLocation());
        if (newCourseClass.getType() != null) courseClass.setType(newCourseClass.getType());
        if (newCourseClass.getTitle() != null) courseClass.setTitle(newCourseClass.getTitle());
        if (newCourseClass.getWeekDay() != null) courseClass.setWeekDay(newCourseClass.getWeekDay());
        if (newCourseClass.getWeeksList() != null) courseClass.setWeeksList(newCourseClass.getWeeksList());

        CourseClass updated = courseClassRepository.save(courseClass);
        log.info(COURSE_CLASS_ACTION, "Successfully updated course class with id={}", id);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        log.info(COURSE_CLASS_ACTION, "Deleting course class with id={}...", id);
        if (courseClassRepository.existsById(id)) {
            courseClassRepository.deleteById(id);
            log.info(COURSE_CLASS_ACTION, "Course class with id={} deleted", id);
        } else {
            log.warn(COURSE_CLASS_ERROR, "Attempt to delete non-existing course class with id={}", id);
            throw notFound.get("error.course-class.notfound", id);
        }
    }
}
