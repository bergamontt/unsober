package ua.unsober.backend.mapper.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.dtos.request.SubjectRequestDto;
import ua.unsober.backend.entities.Subject;

@Component
@RequiredArgsConstructor
public class SubjectRequestMapper {
    public Subject toEntity(SubjectRequestDto dto) {
        if (dto == null)
            return null;
        return Subject.builder()
                .name(dto.getName())
                .annotation(dto.getAnnotation())
                .credits(dto.getCredits())
                .term(dto.getTerm())
                .build();
    }
}
