package ua.unsober.backend.feature.department;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;
    private DepartmentRequestMapper requestMapper;
    private DepartmentResponseMapper responseMapper;
    private LocalizedEntityNotFoundExceptionFactory notFound;

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
                        .orElseThrow(() -> notFound.get("error.department.notfound", id))
        );
    }

    @Override
    public DepartmentResponseDto update(UUID id, DepartmentRequestDto dto) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> notFound.get("error.department.notfound", id));

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
            throw notFound.get("error.department.notfound", id);
        }
    }
}

