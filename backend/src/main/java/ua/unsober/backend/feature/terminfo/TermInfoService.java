package ua.unsober.backend.feature.terminfo;

import ua.unsober.backend.common.enums.Term;

import java.util.List;
import java.util.UUID;

public interface TermInfoService {
    TermInfoResponseDto getById(UUID id);
    TermInfoResponseDto getByStudyYearAndTerm(Integer studyYear, Term term);
    List<TermInfoResponseDto> getAll();
    TermInfoResponseDto create(TermInfoRequestDto dto);
    void deleteById(UUID id);
}
