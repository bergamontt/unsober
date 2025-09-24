package ua.unsober.backend.service;

import ua.unsober.backend.dtos.request.CourseClassRequestDto;
import ua.unsober.backend.dtos.response.CourseClassResponseDto;

import java.util.List;
import java.util.UUID;

public interface CourseClassService {
    CourseClassResponseDto create(CourseClassRequestDto dto);
    List<CourseClassResponseDto> getAll();
    CourseClassResponseDto getById(UUID id);
    CourseClassResponseDto update(UUID id, CourseClassRequestDto dto);
    void delete(UUID id);
}
