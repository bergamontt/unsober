package ua.unsober.backend.feature.faculty;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final FacultyRequestMapper requestMapper;
    private final FacultyResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    private static final Marker FACULTY_ERROR = MarkerFactory.getMarker("FACULTY_ERROR");
    private static final Marker FACULTY_ACTION = MarkerFactory.getMarker("FACULTY_ACTION");

    @Override
    public FacultyResponseDto create(FacultyRequestDto dto) {
        log.info(FACULTY_ACTION, "Creating new faculty...");
        return responseMapper.toDto(
                facultyRepository.save(requestMapper.toEntity(dto))
        );
    }

    @Override
    public List<FacultyResponseDto> getAll() {
        log.info(FACULTY_ACTION, "Fetching all faculties...");
        return facultyRepository.findAll().stream()
                .map(responseMapper::toDto)
                .toList();
    }

    @Override
    public FacultyResponseDto getById(UUID id) {
        log.info(FACULTY_ACTION, "Fetching faculty with id={}...", id);
        return responseMapper.toDto(
                facultyRepository.findById(id).orElseThrow(() -> {
                    log.warn(FACULTY_ERROR, "Attempt to fetch non-existing faculty with id={}", id);
                    return notFound.get("error.faculty.notfound", id);
                })
        );
    }

    @Override
    public FacultyResponseDto update(UUID id, FacultyRequestDto dto) {
        log.info(FACULTY_ACTION, "Updating faculty with id={}...", id);
        Faculty faculty = facultyRepository.findById(id).orElseThrow(() -> {
            log.warn(FACULTY_ERROR, "Attempt to update non-existing faculty with id={}", id);
            return notFound.get("error.faculty.notfound", id);
        });

        Faculty newFaculty = requestMapper.toEntity(dto);

        if (newFaculty.getName() != null) {
            faculty.setName(newFaculty.getName());
        }
        if (newFaculty.getDescription() != null) {
            faculty.setDescription(newFaculty.getDescription());
        }

        Faculty updated = facultyRepository.save(faculty);
        log.info(FACULTY_ACTION, "Successfully updated faculty with id={}", id);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        log.info(FACULTY_ACTION, "Deleting faculty with id={}...", id);
        if (facultyRepository.existsById(id)) {
            facultyRepository.deleteById(id);
            log.info(FACULTY_ACTION, "Faculty with id={} deleted", id);
        } else {
            log.warn(FACULTY_ERROR, "Attempt to delete non-existing faculty with id={}", id);
            throw notFound.get("error.faculty.notfound", id);
        }
    }
}

