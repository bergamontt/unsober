package ua.unsober.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.unsober.backend.dtos.request.FacultyRequestDto;
import ua.unsober.backend.dtos.response.FacultyResponseDto;
import ua.unsober.backend.entities.Faculty;
import ua.unsober.backend.exceptions.LocalizedEntityNotFoundException;
import ua.unsober.backend.mapper.request.FacultyRequestMapper;
import ua.unsober.backend.mapper.response.FacultyResponseMapper;
import ua.unsober.backend.repository.FacultyRepository;
import ua.unsober.backend.service.FacultyService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final FacultyRequestMapper requestMapper;
    private final FacultyResponseMapper responseMapper;
    private final LocalizedEntityNotFoundException notFound;

    @Override
    public FacultyResponseDto create(FacultyRequestDto dto) {
        return responseMapper.toDto(
                facultyRepository.save(requestMapper.toEntity(dto))
        );
    }

    @Override
    public List<FacultyResponseDto> getAll() {
        return facultyRepository.findAll().stream()
                .map(responseMapper::toDto)
                .toList();
    }

    @Override
    public FacultyResponseDto getById(UUID id) {
        return responseMapper.toDto(
                facultyRepository.findById(id)
                        .orElseThrow(() -> notFound.forEntity("error.faculty.notfound", id))
        );
    }

    @Override
    public FacultyResponseDto update(UUID id, FacultyRequestDto dto) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> notFound.forEntity("error.faculty.notfound", id));

        Faculty newFaculty = requestMapper.toEntity(dto);

        if (newFaculty.getName() != null) {
            faculty.setName(newFaculty.getName());
        }
        if (newFaculty.getDescription() != null) {
            faculty.setDescription(newFaculty.getDescription());
        }

        Faculty updated = facultyRepository.save(faculty);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        if (facultyRepository.existsById(id)) {
            facultyRepository.deleteById(id);
        } else {
            throw notFound.forEntity("error.faculty.notfound", id);
        }
    }
}

