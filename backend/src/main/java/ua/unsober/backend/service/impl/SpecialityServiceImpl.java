package ua.unsober.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.unsober.backend.dtos.request.SpecialityRequestDto;
import ua.unsober.backend.dtos.response.SpecialityResponseDto;
import ua.unsober.backend.entities.Speciality;
import ua.unsober.backend.exceptions.LocalizedEntityNotFoundExceptionFactory;
import ua.unsober.backend.mapper.request.SpecialityRequestMapper;
import ua.unsober.backend.mapper.response.SpecialityResponseMapper;
import ua.unsober.backend.repository.SpecialityRepository;
import ua.unsober.backend.service.SpecialityService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecialityServiceImpl implements SpecialityService {

    private final SpecialityRepository specialityRepository;
    private final SpecialityRequestMapper requestMapper;
    private final SpecialityResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    @Override
    public SpecialityResponseDto create(SpecialityRequestDto dto) {
        return responseMapper.toDto(
                specialityRepository.save(requestMapper.toEntity(dto)));
    }

    @Override
    public List<SpecialityResponseDto> getAll() {
        return specialityRepository.findAll()
                .stream()
                .map(responseMapper::toDto)
                .toList();
    }

    @Override
    public SpecialityResponseDto getById(UUID id) {
        return responseMapper.toDto(
                specialityRepository.findById(id).orElseThrow(
                        () -> notFound.get("error.speciality.notfound", id)
                )
        );
    }

    @Override
    public SpecialityResponseDto update(UUID id, SpecialityRequestDto dto) {
        Speciality speciality = specialityRepository.findById(id).orElseThrow(() ->
                notFound.get("error.speciality.notfound", id));
        Speciality newSpeciality = requestMapper.toEntity(dto);

        if (newSpeciality.getDepartment() != null)
            newSpeciality.setDepartment(newSpeciality.getDepartment());
        if (newSpeciality.getName() != null)
            newSpeciality.setName(newSpeciality.getName());
        if (newSpeciality.getDescription() != null)
            newSpeciality.setDescription(newSpeciality.getDescription());

        Speciality updated = specialityRepository.save(speciality);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        if (specialityRepository.existsById(id)) {
            specialityRepository.deleteById(id);
        } else {
            throw notFound.get("error.speciality.notfound", id);
        }
    }

}
