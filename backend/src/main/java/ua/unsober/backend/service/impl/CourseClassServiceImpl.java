package ua.unsober.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.unsober.backend.dtos.request.CourseClassRequestDto;
import ua.unsober.backend.dtos.response.CourseClassResponseDto;
import ua.unsober.backend.entities.CourseClass;
import ua.unsober.backend.exceptions.LocalizedEntityNotFoundException;
import ua.unsober.backend.mapper.request.CourseClassRequestMapper;
import ua.unsober.backend.mapper.response.CourseClassResponseMapper;
import ua.unsober.backend.repository.CourseClassRepository;
import ua.unsober.backend.service.CourseClassService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseClassServiceImpl implements CourseClassService {

    private final CourseClassRepository courseClassRepository;
    private final CourseClassRequestMapper requestMapper;
    private final CourseClassResponseMapper responseMapper;
    private final LocalizedEntityNotFoundException notFound;

    @Override
    public CourseClassResponseDto create(CourseClassRequestDto dto) {
        return responseMapper.toDto(
                courseClassRepository.save(requestMapper.toEntity(dto))
        );
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
                courseClassRepository.findById(id)
                        .orElseThrow(() -> notFound.forEntity("error.course-class.notfound", id))
        );
    }

    @Override
    public CourseClassResponseDto update(UUID id, CourseClassRequestDto dto) {
        CourseClass courseClass = courseClassRepository.findById(id)
                .orElseThrow(() -> notFound.forEntity("error.course-class.notfound", id));

        CourseClass newCourseClass = requestMapper.toEntity(dto);

        if (newCourseClass.getBuilding() != null) {
            courseClass.setBuilding(newCourseClass.getBuilding());
        }
        if (newCourseClass.getClassNumber() != null) {
            courseClass.setClassNumber(newCourseClass.getClassNumber());
        }
        if (newCourseClass.getTeacher() != null) {
            courseClass.setTeacher(newCourseClass.getTeacher());
        }
        if (newCourseClass.getCourse() != null) {
            courseClass.setCourse(newCourseClass.getCourse());
        }
        if (newCourseClass.getGroup() != null) {
            courseClass.setGroup(newCourseClass.getGroup());
        }
        if (newCourseClass.getLocation() != null) {
            courseClass.setLocation(newCourseClass.getLocation());
        }
        if (newCourseClass.getType() != null) {
            courseClass.setType(newCourseClass.getType());
        }
        if (newCourseClass.getTitle() != null) {
            courseClass.setTitle(newCourseClass.getTitle());
        }
        if (newCourseClass.getWeekDay() != null) {
            courseClass.setWeekDay(newCourseClass.getWeekDay());
        }
        if (newCourseClass.getWeeksList() != null) {
            courseClass.setWeeksList(newCourseClass.getWeeksList());
        }
        CourseClass updated = courseClassRepository.save(courseClass);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        if (courseClassRepository.existsById(id)) {
            courseClassRepository.deleteById(id);
        } else {
            throw notFound.forEntity("error.course-class.notfound", id);
        }
    }
}

