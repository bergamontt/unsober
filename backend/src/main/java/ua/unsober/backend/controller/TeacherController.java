
package ua.unsober.backend.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.TeacherRequestDto;
import ua.unsober.backend.dtos.response.TeacherResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private final List<TeacherResponseDto> teachers = new ArrayList<>();

    TeacherController(){
        teachers.add(
                TeacherResponseDto.builder()
                        .id(UUID.randomUUID())
                        .firstName("Олександр")
                        .lastName("Олексієнко")
                        .patronymic("Олександрович")
                        .email("olexandr.olexienko@ukma.edu.ua")
                        .build()
        );
    }

    @PostMapping
    public TeacherResponseDto create(@Valid @RequestBody TeacherRequestDto dto) {
        TeacherResponseDto response = TeacherResponseDto.builder()
                .id(UUID.randomUUID())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .patronymic(dto.getPatronymic())
                .email(dto.getEmail())
                .build();
        teachers.add(response);
        return response;
    }

    @GetMapping
    public List<TeacherResponseDto> getAll() {
        return teachers;
    }

    @GetMapping("/{id}")
    public TeacherResponseDto getById(@PathVariable UUID id) {
        for (TeacherResponseDto teacher : teachers) {
            if (teacher.getId().equals(id))
                return teacher;
        }
        throw new EntityNotFoundException("Teacher with id " + id + " not found");
    }

    @PatchMapping("/{id}")
    public TeacherResponseDto update(
            @PathVariable UUID id,
            @RequestBody TeacherRequestDto dto) {
        TeacherResponseDto teacher = getById(id);
        if(dto.getFirstName() != null)
            teacher.setFirstName(dto.getFirstName());
        if(dto.getLastName() != null)
            teacher.setLastName(dto.getLastName());
        if(dto.getPatronymic() != null)
            teacher.setPatronymic(dto.getPatronymic());
        if(dto.getEmail() != null)
            teacher.setEmail(dto.getEmail());
        return teacher;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        if (teachers.stream().anyMatch(teacher -> teacher.getId().equals(id)))
            teachers.removeIf(teacher -> teacher.getId().equals(id));
        else
            throw new EntityNotFoundException("Teacher with id " + id + " not found");
    }

}
