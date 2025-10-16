package ua.unsober.backend.feature.subject;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
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

    private static final Marker SUBJECT_ACTION = MarkerFactory.getMarker("SUBJECT_ACTION");

    @Override
    public SubjectResponseDto create(SubjectRequestDto dto) {
        log.info(SUBJECT_ACTION, "Creating new subject...");
        Subject saved = subjectRepository.save(requestMapper.toEntity(dto));
        log.info(SUBJECT_ACTION, "Subject created with id={}", saved.getId());
        return responseMapper.toDto(saved);
    }

    @Override
    public Page<SubjectResponseDto> getAll(Pageable pageable) {
        log.info(SUBJECT_ACTION, "Fetching all subjects...");
        Page<SubjectResponseDto> result = subjectRepository.findAll(pageable)
                .map(responseMapper::toDto);
        log.info(SUBJECT_ACTION, "Fetched {} subjects", result.getTotalElements());
        return result;
    }

    @Override
    public SubjectResponseDto getById(UUID id) {
        log.info(SUBJECT_ACTION, "Fetching subject with id={}...", id);
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> {
            log.warn(SUBJECT_ACTION, "Attempt to fetch non-existing subject with id={}", id);
            return notFound.get("error.subject.notfound", id);
        });
        return responseMapper.toDto(subject);
    }

    @Override
    public SubjectResponseDto update(UUID id, SubjectRequestDto dto) {
        log.info(SUBJECT_ACTION, "Updating subject with id={}...", id);
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> {
            log.warn(SUBJECT_ACTION, "Attempt to update non-existing subject with id={}", id);
            return notFound.get("error.subject.notfound", id);
        });

        Subject newSubject = requestMapper.toEntity(dto);

        if (newSubject.getName() != null) subject.setName(newSubject.getName());
        if (newSubject.getAnnotation() != null) subject.setAnnotation(newSubject.getAnnotation());
        if (newSubject.getCredits() != null) subject.setCredits(newSubject.getCredits());
        if (newSubject.getTerm() != null) subject.setTerm(newSubject.getTerm());

        Subject updated = subjectRepository.save(subject);
        log.info(SUBJECT_ACTION, "Successfully updated subject with id={}", id);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        log.info(SUBJECT_ACTION, "Deleting subject with id={}...", id);
        if (subjectRepository.existsById(id)) {
            subjectRepository.deleteById(id);
            log.info(SUBJECT_ACTION, "Subject with id={} deleted", id);
        } else {
            log.warn(SUBJECT_ACTION, "Attempt to delete non-existing subject with id={}", id);
            throw notFound.get("error.subject.notfound", id);
        }
    }
}

