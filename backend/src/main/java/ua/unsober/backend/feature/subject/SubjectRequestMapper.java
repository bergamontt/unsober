package ua.unsober.backend.feature.subject;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
