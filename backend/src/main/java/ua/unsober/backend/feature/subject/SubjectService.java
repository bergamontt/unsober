package ua.unsober.backend.feature.subject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SubjectService {
    SubjectResponseDto create(SubjectRequestDto dto);
    Page<SubjectResponseDto> getAll(Pageable pageable);
    SubjectResponseDto getById(UUID id);
    SubjectResponseDto update(UUID id, SubjectRequestDto dto);
    void delete(UUID id);
}