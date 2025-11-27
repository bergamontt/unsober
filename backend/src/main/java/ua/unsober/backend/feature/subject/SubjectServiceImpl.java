package ua.unsober.backend.feature.subject;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectRequestMapper requestMapper;
    private final SubjectResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    private static final String SUBJECT_NOT_FOUND = "error.subject.notfound";

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
        Subject subject = subjectRepository.findById(id).orElseThrow(() ->
                notFound.get(SUBJECT_NOT_FOUND, id));
        return responseMapper.toDto(subject);
    }

    @Override
    public SubjectResponseDto update(UUID id, SubjectRequestDto dto) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() ->
                notFound.get(SUBJECT_NOT_FOUND, id));

        Subject newSubject = requestMapper.toEntity(dto);

        if (newSubject.getName() != null) subject.setName(newSubject.getName());
        if (newSubject.getAnnotation() != null) subject.setAnnotation(newSubject.getAnnotation());
        if (newSubject.getCredits() != null) subject.setCredits(newSubject.getCredits());
        if (newSubject.getFacultyName() != null) subject.setFacultyName(newSubject.getFacultyName());
        if (newSubject.getDepartmentName() != null) subject.setDepartmentName(newSubject.getDepartmentName());

        Subject updated = subjectRepository.save(subject);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        if (subjectRepository.existsById(id)) {
            subjectRepository.deleteById(id);
        } else {
            throw notFound.get(SUBJECT_NOT_FOUND, id);
        }
    }
}

