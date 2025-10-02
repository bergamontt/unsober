package ua.unsober.backend.feature.speciality;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;
import ua.unsober.backend.feature.department.DepartmentRepository;

@Component
@RequiredArgsConstructor
public class SpecialityRequestMapper {
    private final DepartmentRepository departmentRepository;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    public Speciality toEntity(SpecialityRequestDto dto) {
        if (dto == null) return null;
        Speciality entity = Speciality.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();

        java.util.UUID departmentId = dto.getDepartmentId();
        if (departmentId != null) {
            entity.setDepartment(departmentRepository.findById(departmentId).orElseThrow(() ->
                    notFound.get("error.department.notfound", departmentId)));
        }
        return entity;
    }
}
