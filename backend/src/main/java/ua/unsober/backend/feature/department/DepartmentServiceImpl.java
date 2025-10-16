package ua.unsober.backend.feature.department;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentRequestMapper requestMapper;
    private final DepartmentResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    private static final Marker DEPARTMENT_ERROR = MarkerFactory.getMarker("DEPARTMENT_ERROR");
    private static final Marker DEPARTMENT_ACTION = MarkerFactory.getMarker("DEPARTMENT_ACTION");

    @Override
    public DepartmentResponseDto create(DepartmentRequestDto dto) {
        log.info(DEPARTMENT_ACTION, "Creating new department...");
        Department saved = departmentRepository.save(requestMapper.toEntity(dto));
        log.info(DEPARTMENT_ACTION, "Department created with id={}", saved.getId());
        return responseMapper.toDto(saved);
    }

    @Override
    public List<DepartmentResponseDto> getAll() {
        log.info(DEPARTMENT_ACTION, "Fetching all departments...");
        List<DepartmentResponseDto> result = departmentRepository.findAll().stream()
                .map(responseMapper::toDto)
                .toList();
        log.info(DEPARTMENT_ACTION, "Fetched {} departments", result.size());
        return result;
    }

    @Override
    public DepartmentResponseDto getById(UUID id) {
        log.info(DEPARTMENT_ACTION, "Fetching department with id={}...", id);
        return responseMapper.toDto(
                departmentRepository.findById(id).orElseThrow(() -> {
                    log.warn(DEPARTMENT_ERROR, "Attempt to fetch non-existing department with id={}", id);
                    return notFound.get("error.department.notfound", id);
                })
        );
    }

    @Override
    public DepartmentResponseDto update(UUID id, DepartmentRequestDto dto) {
        log.info(DEPARTMENT_ACTION, "Updating department with id={}...", id);
        Department department = departmentRepository.findById(id).orElseThrow(() -> {
            log.warn(DEPARTMENT_ERROR, "Attempt to update non-existing department with id={}", id);
            return notFound.get("error.department.notfound", id);
        });

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
        log.info(DEPARTMENT_ACTION, "Successfully updated department with id={}", id);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        log.info(DEPARTMENT_ACTION, "Deleting department with id={}...", id);
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
            log.info(DEPARTMENT_ACTION, "Department with id={} deleted", id);
        } else {
            log.warn(DEPARTMENT_ERROR, "Attempt to delete non-existing department with id={}", id);
            throw notFound.get("error.department.notfound", id);
        }
    }
}

