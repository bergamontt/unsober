package ua.unsober.backend.feature.course;

import java.util.List;
import java.util.UUID;

public interface CourseService {
    CourseResponseDto create(CourseRequestDto dto);
    List<CourseResponseDto> getAll();
    CourseResponseDto getById(UUID id);
    CourseResponseDto update(UUID id, CourseRequestDto dto);
    void delete(UUID id);
}
