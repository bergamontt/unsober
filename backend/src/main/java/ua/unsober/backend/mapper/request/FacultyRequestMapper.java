package ua.unsober.backend.mapper.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.dtos.request.FacultyRequestDto;
import ua.unsober.backend.entities.Faculty;

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
}
