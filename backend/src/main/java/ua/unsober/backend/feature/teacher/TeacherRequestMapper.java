package ua.unsober.backend.feature.teacher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeacherRequestMapper {
    public Teacher toEntity(TeacherRequestDto dto) {
        if (dto == null) return null;
        return Teacher.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .patronymic(dto.getPatronymic())
                .email(dto.getEmail())
                .build();
    }

    public TeacherRequestDto toDto(Teacher entity) {
        if (entity == null) return null;
        return TeacherRequestDto.builder()
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .patronymic(entity.getPatronymic())
                .email(entity.getEmail())
                .build();
    }
}
