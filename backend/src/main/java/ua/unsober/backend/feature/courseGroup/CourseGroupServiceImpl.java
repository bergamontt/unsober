package ua.unsober.backend.feature.courseGroup;

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
public class CourseGroupServiceImpl implements CourseGroupService {

    private final CourseGroupRepository courseGroupRepository;
    private final CourseGroupRequestMapper requestMapper;
    private final CourseGroupResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    private static final Marker COURSE_GROUP_ACTION = MarkerFactory.getMarker("COURSE_GROUP_ACTION");
    private static final Marker COURSE_GROUP_ERROR = MarkerFactory.getMarker("COURSE_GROUP_ERROR");

    @Override
    public CourseGroupResponseDto create(CourseGroupRequestDto dto) {
        log.info(COURSE_GROUP_ACTION, "Creating new course group...");
        CourseGroup saved = courseGroupRepository.save(requestMapper.toEntity(dto));
        log.info(COURSE_GROUP_ACTION, "Course group created with id={}", saved.getId());
        return responseMapper.toDto(saved);
    }

    @Override
    public List<CourseGroupResponseDto> getAll() {
        log.info(COURSE_GROUP_ACTION, "Fetching all course groups...");
        List<CourseGroupResponseDto> result = courseGroupRepository.findAll().stream()
                .map(responseMapper::toDto)
                .toList();
        log.info(COURSE_GROUP_ACTION, "Fetched {} course groups", result.size());
        return result;
    }

    @Override
    public CourseGroupResponseDto getById(UUID id) {
        log.info(COURSE_GROUP_ACTION, "Fetching course group with id={}...", id);
        return responseMapper.toDto(
                courseGroupRepository.findById(id).orElseThrow(() -> {
                    log.warn(COURSE_GROUP_ERROR, "Attempt to fetch non-existing course group with id={}", id);
                    return notFound.get("error.course-group.notfound", id);
                })
        );
    }

    @Override
    public CourseGroupResponseDto update(UUID id, CourseGroupRequestDto dto) {
        log.info(COURSE_GROUP_ACTION, "Updating course group with id={}...", id);
        CourseGroup courseGroup = courseGroupRepository.findById(id).orElseThrow(() -> {
            log.warn(COURSE_GROUP_ERROR, "Attempt to update non-existing course group with id={}", id);
            return notFound.get("error.course-group.notfound", id);
        });

        CourseGroup newCourseGroup = requestMapper.toEntity(dto);

        if (newCourseGroup.getGroupNumber() != null) {
            courseGroup.setGroupNumber(newCourseGroup.getGroupNumber());
        }
        if (newCourseGroup.getMaxStudents() != null) {
            courseGroup.setMaxStudents(newCourseGroup.getMaxStudents());
        }
        if (newCourseGroup.getCourse() != null) {
            courseGroup.setCourse(newCourseGroup.getCourse());
        }

        CourseGroup updated = courseGroupRepository.save(courseGroup);
        log.info(COURSE_GROUP_ACTION, "Successfully updated course group with id={}", id);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        log.info(COURSE_GROUP_ACTION, "Deleting course group with id={}...", id);
        if (courseGroupRepository.existsById(id)) {
            courseGroupRepository.deleteById(id);
            log.info(COURSE_GROUP_ACTION, "Course group with id={} deleted", id);
        } else {
            log.warn(COURSE_GROUP_ERROR, "Attempt to delete non-existing course group with id={}", id);
            throw notFound.get("error.course-group.notfound", id);
        }
    }
}

