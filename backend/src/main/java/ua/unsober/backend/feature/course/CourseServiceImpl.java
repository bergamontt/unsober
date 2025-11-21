package ua.unsober.backend.feature.course;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseRequestMapper requestMapper;
    private final CourseResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    @Override
    @CacheEvict(value = "coursesPage", allEntries = true)
    @CachePut(value = "courseById", key = "#result.id")
    public CourseResponseDto create(CourseRequestDto dto) {
        Course saved = courseRepository.save(requestMapper.toEntity(dto));
        return responseMapper.toDto(saved);
    }

    @Override
    @Cacheable(value = "coursesPage", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<CourseResponseDto> getAll(Pageable pageable) {
        return courseRepository.findAll(pageable)
                .map(responseMapper::toDto);
    }

    @Override
    @Cacheable(value = "courseById", key = "#id")
    public CourseResponseDto getById(UUID id) {
        return responseMapper.toDto(
                courseRepository.findById(id).orElseThrow(() -> notFound.get("error.course.notfound", id))
        );
    }

    @Override
    @CacheEvict(value = "coursesPage", allEntries = true)
    @CachePut(value = "courseById", key = "#id")
    public CourseResponseDto update(UUID id, CourseRequestDto dto) {
        Course course = courseRepository.findById(id).orElseThrow(() -> notFound.get("error.course.notfound", id));

        Course newCourse = requestMapper.toEntity(dto);

        if (newCourse.getSubject() != null) course.setSubject(newCourse.getSubject());
        if (newCourse.getMaxStudents() != null) course.setMaxStudents(newCourse.getMaxStudents());
        if (newCourse.getCourseYear() != null) course.setCourseYear(newCourse.getCourseYear());

        Course updated = courseRepository.save(course);
        return responseMapper.toDto(updated);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "courseById", key = "#id"),
            @CacheEvict(value = "coursesPage", allEntries = true)
    })
    public void delete(UUID id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
        } else {
            throw notFound.get("error.course.notfound", id);
        }
    }
}

