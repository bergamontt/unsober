package ua.unsober.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.unsober.backend.dtos.request.DepartmentRequestDto;
import ua.unsober.backend.dtos.response.DepartmentResponseDto;
import ua.unsober.backend.entities.Department;
import ua.unsober.backend.exceptions.LocalizedEntityNotFoundException;
import ua.unsober.backend.mapper.request.DepartmentRequestMapper;
import ua.unsober.backend.mapper.response.DepartmentResponseMapper;
import ua.unsober.backend.repository.DepartmentRepository;
import ua.unsober.backend.service.DepartmentService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentRequestMapper requestMapper;
    private final DepartmentResponseMapper responseMapper;
    private final LocalizedEntityNotFoundException notFound;

    @Override
    public DepartmentResponseDto create(DepartmentRequestDto dto) {
        return responseMapper.toDto(
                departmentRepository.save(requestMapper.toEntity(dto))
        );
    }

    @Override
    public List<DepartmentResponseDto> getAll() {
        return departmentRepository.findAll().stream()
                .map(responseMapper::toDto)
                .toList();
    }

    @Override
    public DepartmentResponseDto getById(UUID id) {
        return responseMapper.toDto(
                departmentRepository.findById(id)
                        .orElseThrow(() -> notFound.forEntity("error.department.notfound", id))
        );
    }

    @Override
    public DepartmentResponseDto update(UUID id, DepartmentRequestDto dto) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> notFound.forEntity("error.department.notfound", id));

        Department newDepartment = requestMapper.toEntity(dto);

        if (newDepartment.getName() != null) {
            department.setName(newDepartment.getName());
        }
        if (newDepartment.getDescription() != null) {
            department.setDescription(newDepartment.getDescription());
        }
        if (newDepartment.getFaculty() != null) {
            department.setFaculty(newDepartment.getFaculty());
        }

        Department updated = departmentRepository.save(department);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
        } else {
            throw notFound.forEntity("error.department.notfound", id);
        }
    }
}

