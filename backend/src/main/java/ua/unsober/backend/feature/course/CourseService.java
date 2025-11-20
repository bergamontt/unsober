package ua.unsober.backend.feature.course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CourseService {
    CourseResponseDto create(CourseRequestDto dto);
    Page<CourseResponseDto> getAll(Pageable pageable);
    CourseResponseDto getById(UUID id);
    CourseResponseDto update(UUID id, CourseRequestDto dto);
    void delete(UUID id);
}
