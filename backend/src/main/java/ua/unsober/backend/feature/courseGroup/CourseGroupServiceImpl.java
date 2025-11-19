package ua.unsober.backend.feature.courseGroup;

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
    public CourseGroupResponseDto getById(UUID id) {
        return responseMapper.toDto(
                courseGroupRepository.findById(id).orElseThrow(() -> {
                    return notFound.get("error.course-group.notfound", id);
                })
        );
    }

    @Override
    public CourseGroupResponseDto update(UUID id, CourseGroupRequestDto dto) {
        CourseGroup courseGroup = courseGroupRepository.findById(id).orElseThrow(() -> {
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
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        if (courseGroupRepository.existsById(id)) {
            courseGroupRepository.deleteById(id);
        } else {
            throw notFound.get("error.course-group.notfound", id);
        }
    }
}

