package ua.unsober.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.unsober.backend.dtos.request.SubjectRequestDto;
import ua.unsober.backend.dtos.response.SubjectResponseDto;
import ua.unsober.backend.entities.Subject;
import ua.unsober.backend.exceptions.LocalizedEntityNotFoundException;
import ua.unsober.backend.mapper.request.SubjectRequestMapper;
import ua.unsober.backend.mapper.response.SubjectResponseMapper;
import ua.unsober.backend.repository.SubjectRepository;
import ua.unsober.backend.service.SubjectService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectRequestMapper requestMapper;
    private final SubjectResponseMapper responseMapper;
    private final LocalizedEntityNotFoundException notFound;

    @Override
    public SubjectResponseDto create(SubjectRequestDto dto) {
        return responseMapper.toDto(
                subjectRepository.save(requestMapper.toEntity(dto)));
    }

    @Override
    public Page<SubjectResponseDto> getAll(Pageable pageable) {
        return subjectRepository.findAll(pageable).map(responseMapper::toDto);
    }

    @Override
    public SubjectResponseDto getById(UUID id) {
        return responseMapper.toDto(
                subjectRepository.findById(id).orElseThrow(() ->
                        notFound.forEntity("error.subject.notfound", id)));
    }

    @Override
    public SubjectResponseDto update(UUID id, SubjectRequestDto dto) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() ->
                notFound.forEntity("error.subject.notfound", id));
        Subject newSubject = requestMapper.toEntity(dto);
        if (newSubject.getName() != null)
            subject.setName(newSubject.getName());
        if (newSubject.getAnnotation() != null)
            newSubject.setAnnotation(newSubject.getAnnotation());
        if (newSubject.getCredits() != null)
            subject.setCredits(newSubject.getCredits());
        if (newSubject.getTerm() != null)
            subject.setTerm(newSubject.getTerm());
        Subject updated = subjectRepository.save(newSubject);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        if (subjectRepository.existsById(id)) {
            subjectRepository.deleteById(id);
        } else {
            throw notFound.forEntity("error.subject.notfound", id);
        }
    }
}
