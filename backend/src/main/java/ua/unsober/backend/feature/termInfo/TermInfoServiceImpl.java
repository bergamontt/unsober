package ua.unsober.backend.feature.termInfo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.enums.Term;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TermInfoServiceImpl implements TermInfoService {
    private final TermInfoRepository termInfoRepository;
    private final TermInfoRequestMapper requestMapper;
    private final TermInfoResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    @Override
    public TermInfoResponseDto getById(UUID id) {
        return responseMapper.toDto(
                termInfoRepository.findById(id).orElseThrow(() ->
                        notFound.get("error.term-info.notfound", id))
        );
    }

    @Override
    public TermInfoResponseDto getByYearAndTerm(Integer year, Term term) {
        return responseMapper.toDto(
                termInfoRepository.getByYearAndTerm(year, term).orElseThrow(() ->
                        notFound.get("error.term-info-for-year.notfound", term.name(), year))
        );
    }

    @Override
    public List<TermInfoResponseDto> getAll() {
        return termInfoRepository.findAll()
                .stream()
                .map(responseMapper::toDto)
                .toList();
    }

    @Override
    public TermInfoResponseDto create(TermInfoRequestDto dto) {
        return responseMapper.toDto(termInfoRepository.save(requestMapper.toEntity(dto)));
    }

    @Override
    public void deleteById(UUID id) {
        if (termInfoRepository.existsById(id)) {
            termInfoRepository.deleteById(id);
        } else {
            throw notFound.get("error.term.notfound", id);
        }
    }
}
