package ua.unsober.backend.feature.subject;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectRequestMapper requestMapper;
    private final SubjectResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    @Override
    public SubjectResponseDto create(SubjectRequestDto dto) {
        Subject saved = subjectRepository.save(requestMapper.toEntity(dto));
        return responseMapper.toDto(saved);
    }

    @Override
    public Page<SubjectResponseDto> getAll(Pageable pageable) {
        return subjectRepository.findAll(pageable)
                .map(responseMapper::toDto);
    }

    @Override
    public SubjectResponseDto getById(UUID id) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> {
            return notFound.get("error.subject.notfound", id);
        });
        return responseMapper.toDto(subject);
    }

    @Override
    public SubjectResponseDto update(UUID id, SubjectRequestDto dto) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> {
            return notFound.get("error.subject.notfound", id);
        });

        Subject newSubject = requestMapper.toEntity(dto);

        if (newSubject.getName() != null) subject.setName(newSubject.getName());
        if (newSubject.getAnnotation() != null) subject.setAnnotation(newSubject.getAnnotation());
        if (newSubject.getCredits() != null) subject.setCredits(newSubject.getCredits());
        if (newSubject.getTerm() != null) subject.setTerm(newSubject.getTerm());

        Subject updated = subjectRepository.save(subject);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        if (subjectRepository.existsById(id)) {
            subjectRepository.deleteById(id);
        } else {
            throw notFound.get("error.subject.notfound", id);
        }
    }
}

