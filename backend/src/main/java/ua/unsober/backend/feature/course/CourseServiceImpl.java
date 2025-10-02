package ua.unsober.backend.feature.course;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseRequestMapper requestMapper;
    private final CourseResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    @Override
    public CourseResponseDto create(CourseRequestDto dto) {
        return responseMapper.toDto(
                courseRepository.save(requestMapper.toEntity(dto)));
    }

    @Override
    public List<CourseResponseDto> getAll() {
        return courseRepository.findAll()
                .stream()
                .map(responseMapper::toDto)
                .toList();
    }

    @Override
    public CourseResponseDto getById(UUID id) {
        return responseMapper.toDto(
                courseRepository.findById(id).orElseThrow(() ->
                        notFound.get("error.course.notfound", id)));
    }

    @Override
    public CourseResponseDto update(UUID id, CourseRequestDto dto) {
        Course course = courseRepository.findById(id).orElseThrow(() ->
                notFound.get("error.course.notfound", id));
        Course newCourse = requestMapper.toEntity(dto);
        if (newCourse.getSubject() != null)
            course.setSubject(newCourse.getSubject());
        if (newCourse.getMaxStudents() != null)
            course.setMaxStudents(newCourse.getMaxStudents());
        if (newCourse.getCourseYear() != null)
            course.setCourseYear(newCourse.getCourseYear());
        Course updated = courseRepository.save(course);
        return responseMapper.toDto(updated);
    }


    @Override
    public void delete(UUID id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
        } else {
            throw notFound.get("error.course.notfound", id);
        }
    }
}
