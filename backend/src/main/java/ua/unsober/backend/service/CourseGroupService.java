package ua.unsober.backend.service;

import ua.unsober.backend.dtos.request.CourseGroupRequestDto;
import ua.unsober.backend.dtos.response.CourseGroupResponseDto;

import java.util.List;
import java.util.UUID;

public interface CourseGroupService {
    CourseGroupResponseDto create(CourseGroupRequestDto dto);
    List<CourseGroupResponseDto> getAll();
    CourseGroupResponseDto getById(UUID id);
    CourseGroupResponseDto update(UUID id, CourseGroupRequestDto dto);
    void delete(UUID id);
}
