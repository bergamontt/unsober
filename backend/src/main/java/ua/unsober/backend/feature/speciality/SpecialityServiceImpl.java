package ua.unsober.backend.feature.speciality;

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
public class SpecialityServiceImpl implements SpecialityService {

    private final SpecialityRepository specialityRepository;
    private final SpecialityRequestMapper requestMapper;
    private final SpecialityResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    private static final Marker SPECIALITY_ERROR = MarkerFactory.getMarker("SPECIALITY_ERROR");
    private static final Marker SPECIALITY_ACTION = MarkerFactory.getMarker("SPECIALITY_ACTION");

    @Override
    public SpecialityResponseDto create(SpecialityRequestDto dto) {
        log.info(SPECIALITY_ACTION, "Creating new speciality...");
        return responseMapper.toDto(
                specialityRepository.save(requestMapper.toEntity(dto)));
    }

    @Override
    public List<SpecialityResponseDto> getAll() {
        log.info(SPECIALITY_ACTION, "Fetching all specialities...");
        return specialityRepository.findAll()
                .stream()
                .map(responseMapper::toDto)
                .toList();
    }

    @Override
    public SpecialityResponseDto getById(UUID id) {
        log.info(SPECIALITY_ACTION, "Fetching speciality with id={}...", id);
        return responseMapper.toDto(
                specialityRepository.findById(id).orElseThrow(() -> {
                    log.warn(SPECIALITY_ERROR, "Attempt to fetch non-existing speciality with id={}", id);
                    return notFound.get("error.speciality.notfound", id);
                })
        );
    }

    @Override
    public SpecialityResponseDto update(UUID id, SpecialityRequestDto dto) {
        log.info(SPECIALITY_ACTION, "Updating speciality with id={}...", id);
        Speciality speciality = specialityRepository.findById(id).orElseThrow(() -> {
            log.warn(SPECIALITY_ERROR, "Attempt to update non-existing speciality with id={}", id);
            return notFound.get("error.speciality.notfound", id);
        });

        Speciality newSpeciality = requestMapper.toEntity(dto);

        if (newSpeciality.getDepartment() != null)
            speciality.setDepartment(newSpeciality.getDepartment());
        if (newSpeciality.getName() != null)
            speciality.setName(newSpeciality.getName());
        if (newSpeciality.getDescription() != null)
            speciality.setDescription(newSpeciality.getDescription());

        Speciality updated = specialityRepository.save(speciality);
        log.info(SPECIALITY_ACTION, "Successfully updated speciality with id={}", id);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        log.info(SPECIALITY_ACTION, "Deleting speciality with id={}...", id);
        if (specialityRepository.existsById(id)) {
            specialityRepository.deleteById(id);
            log.info(SPECIALITY_ACTION, "Speciality with id={} deleted", id);
        } else {
            log.warn(SPECIALITY_ERROR, "Attempt to delete non-existing speciality with id={}", id);
            throw notFound.get("error.speciality.notfound", id);
        }
    }

}

