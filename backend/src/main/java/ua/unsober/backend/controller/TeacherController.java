
package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.TeacherRequestDto;
import ua.unsober.backend.dtos.response.TeacherResponseDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @PostMapping
    public TeacherResponseDto create(@Valid @RequestBody TeacherRequestDto dto) {
        return TeacherResponseDto.builder()
                .id(UUID.randomUUID())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .patronymic(dto.getPatronymic())
                .email(dto.getEmail())
                .build();
    }

    @GetMapping
    public List<TeacherResponseDto> getAll() {
        return List.of(
                TeacherResponseDto.builder()
                        .id(UUID.randomUUID())
                        .firstName("Олександр")
                        .lastName("Олексієнко")
                        .patronymic("Олександрович")
                        .email("olexandr.olexienko@ukma.edu.ua")
                        .build()
        );
    }

    @GetMapping("/{id}")
    public TeacherResponseDto getById(@PathVariable UUID id) {
        return TeacherResponseDto.builder()
                .id(id)
                .firstName("Олександр")
                .lastName("Олексієнко")
                .patronymic("Олександрович")
                .email("olexandr.olexienko@ukma.edu.ua")
                .build();
    }

    @PatchMapping("/{id}")
    public TeacherResponseDto update(
            @PathVariable UUID id,
            @RequestBody TeacherRequestDto dto) {
        return TeacherResponseDto.builder()
                .id(id)
                .firstName(Optional.ofNullable(dto.getFirstName()).orElse("Олександр"))
                .lastName(Optional.ofNullable(dto.getLastName()).orElse("Олексієнко"))
                .patronymic(Optional.ofNullable(dto.getPatronymic()).orElse("Олександрович"))
                .email(Optional.ofNullable(dto.getEmail()).orElse("olexandr.olexienko@ukma.edu.ua"))
                .build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {}

}
