package ua.unsober.backend.feature.faculty;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FacultyRequestMapper {
    public Faculty toEntity(FacultyRequestDto dto) {
        if (dto == null)
            return null;
        return Faculty.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public FacultyRequestDto toDto(Faculty entity) {
        if (entity == null) return null;
        return new FacultyRequestDto(
                entity.getName(),
                entity.getDescription()
        );
    }
}
