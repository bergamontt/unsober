package ua.unsober.backend.feature.coursegroup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private static final String GROUP_NOT_FOUND = "error.course-group.notfound";

    @Override
    public CourseGroupResponseDto create(CourseGroupRequestDto dto) {
        CourseGroup saved = courseGroupRepository.save(requestMapper.toEntity(dto));
        return responseMapper.toDto(saved);
    }

    @Override
    public List<CourseGroupResponseDto> getAll() {
        return courseGroupRepository.findAll().stream()
                .map(responseMapper::toDto)
                .toList();
    }

    @Override
    public List<CourseGroupResponseDto> getAllByCourseId(UUID courseId) {
        return courseGroupRepository.findByCourseId(courseId)
                .stream()
                .map(responseMapper::toDto)
                .toList();
    }

    @Override
    public CourseGroupResponseDto getById(UUID id) {
        return responseMapper.toDto(
                courseGroupRepository.findById(id).orElseThrow(() ->
                        notFound.get(GROUP_NOT_FOUND, id))
        );
    }

    @Override
    public CourseGroupResponseDto update(UUID id, CourseGroupRequestDto dto) {
        CourseGroup courseGroup = courseGroupRepository.findById(id).orElseThrow(() ->
                notFound.get(GROUP_NOT_FOUND, id));

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
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        if (courseGroupRepository.existsById(id)) {
            courseGroupRepository.deleteById(id);
        } else {
            throw notFound.get(GROUP_NOT_FOUND, id);
        }
    }
}

