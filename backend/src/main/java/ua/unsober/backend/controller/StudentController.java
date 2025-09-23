package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.StudentRequestDto;
import ua.unsober.backend.dtos.response.SpecialityResponseDto;
import ua.unsober.backend.dtos.response.StudentResponseDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/students")
public class StudentController {

    @PostMapping
    public StudentResponseDto create(@Valid @RequestBody StudentRequestDto dto) {
        return StudentResponseDto.builder()
                .id(UUID.randomUUID())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .patronymic(dto.getPatronymic())
                .recordBookNumber(dto.getRecordBookNumber())
                .email(dto.getEmail())
                .specialty(new SpecialityResponseDto())
                .studyYear(dto.getStudyYear())
                .build();
    }

    @GetMapping
    public List<StudentResponseDto> getAll() {
        return List.of(
                StudentResponseDto.builder()
                        .id(UUID.randomUUID())
                        .firstName("Іван")
                        .lastName("Іваненко")
                        .patronymic("Іванович")
                        .recordBookNumber("RB123456")
                        .email("ivan.ivanenko@ukma.edu.ua")
                        .specialty(new SpecialityResponseDto())
                        .studyYear(1)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public StudentResponseDto getById(@PathVariable UUID id) {
        return StudentResponseDto.builder()
                .id(id)
                .firstName("Іван")
                .lastName("Іваненко")
                .patronymic("Іванович")
                .recordBookNumber("RB123456")
                .email("ivan.ivanenko@ukma.edu.ua")
                .specialty(new SpecialityResponseDto())
                .studyYear(1)
                .build();
    }

    @PatchMapping("/{id}")
    public StudentResponseDto update(
            @PathVariable UUID id,
            @RequestBody StudentRequestDto dto) {
        return StudentResponseDto.builder()
                .id(id)
                .firstName(Optional.ofNullable(dto.getFirstName()).orElse("Іван"))
                .lastName(Optional.ofNullable(dto.getLastName()).orElse("Іваненко"))
                .patronymic(Optional.ofNullable(dto.getPatronymic()).orElse("Іванович"))
                .recordBookNumber(Optional.ofNullable(dto.getRecordBookNumber()).orElse("RB123456"))
                .email(Optional.ofNullable(dto.getEmail()).orElse("ivan.ivanenko@ukma.edu.ua"))
                .specialty(new SpecialityResponseDto())
                .studyYear(Optional.ofNullable(dto.getStudyYear()).orElse(1))
                .build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {}
}
