package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.CourseClassRequestDto;
import ua.unsober.backend.dtos.response.CourseClassResponseDto;
import ua.unsober.backend.dtos.response.CourseResponseDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/course-classes")
public class CourseClassController {

    @PostMapping
    public CourseClassResponseDto create(@Valid @RequestBody CourseClassRequestDto dto) {
        return CourseClassResponseDto.builder()
                .id(UUID.randomUUID())
                .course(new CourseResponseDto())
                .title(dto.getTitle())
                .type(dto.getType())
                .weeksList(dto.getWeeksList())
                .weekDay(dto.getWeekDay())
                .classNumber(dto.getClassNumber())
                .location(dto.getLocation())
                .build();
    }

    @GetMapping
    public List<CourseClassResponseDto> getAll() {
        return List.of(
                CourseClassResponseDto.builder()
                        .id(UUID.randomUUID())
                        .course(new CourseResponseDto())
                        .title("Лекція з ООП")
                        .type("Lecture")
                        .weeksList(List.of(1, 2, 3))
                        .weekDay("Monday")
                        .classNumber(3)
                        .location("1-223")
                        .build()
        );
    }

    @GetMapping("/{id}")
    public CourseClassResponseDto getById(@PathVariable UUID id) {
        return CourseClassResponseDto.builder()
                .id(id)
                .course(new CourseResponseDto())
                .title("Лекція з ООП")
                .type("Lecture")
                .weeksList(List.of(1, 2, 3))
                .weekDay("Monday")
                .classNumber(3)
                .location("1-223")
                .build();
    }

    @PatchMapping("/{id}")
    public CourseClassResponseDto update(
            @PathVariable UUID id,
            @RequestBody CourseClassRequestDto dto) {
        return CourseClassResponseDto.builder()
                .id(id)
                .course(new CourseResponseDto())
                .title(dto.getTitle() == null ? "Лекція з ООП" : dto.getTitle())
                .type(dto.getType() == null ? "Lecture" : dto.getType())
                .weeksList(dto.getWeeksList()  == null ? List.of(1, 2, 3) : dto.getWeeksList())
                .weekDay(dto.getWeekDay() == null ? "Monday" : dto.getWeekDay())
                .classNumber(dto.getClassNumber() == null ? 3 : dto.getClassNumber())
                .location(dto.getLocation() == null ? "1-223" : dto.getLocation())
                .build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {}
}

