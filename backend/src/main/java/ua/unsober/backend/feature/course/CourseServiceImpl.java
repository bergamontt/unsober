package ua.unsober.backend.feature.course;

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
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseRequestMapper requestMapper;
    private final CourseResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    private static final Marker COURSE_ACTION = MarkerFactory.getMarker("COURSE_ACTION");
    private static final Marker COURSE_ERROR = MarkerFactory.getMarker("COURSE_ERROR");

    @Override
    public CourseResponseDto create(CourseRequestDto dto) {
        log.info(COURSE_ACTION, "Creating new course...");
        Course saved = courseRepository.save(requestMapper.toEntity(dto));
        log.info(COURSE_ACTION, "Course created with id={}", saved.getId());
        return responseMapper.toDto(saved);
    }

    @Override
    public List<CourseResponseDto> getAll() {
        log.info(COURSE_ACTION, "Fetching all courses...");
        List<CourseResponseDto> result = courseRepository.findAll()
                .stream()
                .map(responseMapper::toDto)
                .toList();
        log.info(COURSE_ACTION, "Fetched {} courses", result.size());
        return result;
    }

    @Override
    public CourseResponseDto getById(UUID id) {
        log.info(COURSE_ACTION, "Fetching course with id={}...", id);
        return responseMapper.toDto(
                courseRepository.findById(id).orElseThrow(() -> {
                    log.warn(COURSE_ERROR, "Attempt to fetch non-existing course with id={}", id);
                    return notFound.get("error.course.notfound", id);
                })
        );
    }

    @Override
    public CourseResponseDto update(UUID id, CourseRequestDto dto) {
        log.info(COURSE_ACTION, "Updating course with id={}...", id);
        Course course = courseRepository.findById(id).orElseThrow(() -> {
            log.warn(COURSE_ERROR, "Attempt to update non-existing course with id={}", id);
            return notFound.get("error.course.notfound", id);
        });

        Course newCourse = requestMapper.toEntity(dto);

        if (newCourse.getSubject() != null) course.setSubject(newCourse.getSubject());
        if (newCourse.getMaxStudents() != null) course.setMaxStudents(newCourse.getMaxStudents());
        if (newCourse.getCourseYear() != null) course.setCourseYear(newCourse.getCourseYear());

        Course updated = courseRepository.save(course);
        log.info(COURSE_ACTION, "Successfully updated course with id={}", id);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        log.info(COURSE_ACTION, "Deleting course with id={}...", id);
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            log.info(COURSE_ACTION, "Course with id={} deleted", id);
        } else {
            log.warn(COURSE_ERROR, "Attempt to delete non-existing course with id={}", id);
            throw notFound.get("error.course.notfound", id);
        }
    }
}

