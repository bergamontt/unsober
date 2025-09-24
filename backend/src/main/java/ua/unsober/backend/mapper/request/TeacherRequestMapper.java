package ua.unsober.backend.mapper.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.dtos.request.TeacherRequestDto;
import ua.unsober.backend.entities.Teacher;

@Component
@RequiredArgsConstructor
public class TeacherRequestMapper {
    public Teacher toEntity(TeacherRequestDto dto) {
        if (dto == null)
            return null;
        return Teacher.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .patronymic(dto.getPatronymic())
                .email(dto.getEmail())
                .build();
    }
}
