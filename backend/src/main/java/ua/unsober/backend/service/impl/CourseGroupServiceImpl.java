package ua.unsober.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.unsober.backend.dtos.request.CourseGroupRequestDto;
import ua.unsober.backend.dtos.response.CourseGroupResponseDto;
import ua.unsober.backend.entities.CourseGroup;
import ua.unsober.backend.exceptions.LocalizedEntityNotFoundException;
import ua.unsober.backend.mapper.request.CourseGroupRequestMapper;
import ua.unsober.backend.mapper.response.CourseGroupResponseMapper;
import ua.unsober.backend.repository.CourseGroupRepository;
import ua.unsober.backend.service.CourseGroupService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseGroupServiceImpl implements CourseGroupService {

    private final CourseGroupRepository courseGroupRepository;
    private final CourseGroupRequestMapper requestMapper;
    private final CourseGroupResponseMapper responseMapper;
    private final LocalizedEntityNotFoundException notFound;

    @Override
    public CourseGroupResponseDto create(CourseGroupRequestDto dto) {
        return responseMapper.toDto(
                courseGroupRepository.save(requestMapper.toEntity(dto))
        );
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
                courseGroupRepository.findById(id)
                        .orElseThrow(() -> notFound.forEntity("error.course-group.notfound", id))
        );
    }

    @Override
    public CourseGroupResponseDto update(UUID id, CourseGroupRequestDto dto) {
        CourseGroup courseGroup = courseGroupRepository.findById(id)
                .orElseThrow(() -> notFound.forEntity("error.course-group.notfound", id));

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
            throw notFound.forEntity("error.course-group.notfound", id);
        }
    }
}

