package ua.unsober.backend.feature.faculty;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final FacultyRequestMapper requestMapper;
    private final FacultyResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

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
                        .orElseThrow(() -> notFound.get("error.faculty.notfound", id))
        );
    }

    @Override
    public FacultyResponseDto update(UUID id, FacultyRequestDto dto) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> notFound.get("error.faculty.notfound", id));

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
            throw notFound.get("error.faculty.notfound", id);
        }
    }
}

