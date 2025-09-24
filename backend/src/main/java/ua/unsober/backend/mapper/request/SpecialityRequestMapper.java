package ua.unsober.backend.mapper.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.dtos.request.SpecialityRequestDto;
import ua.unsober.backend.entities.Speciality;
import ua.unsober.backend.exceptions.LocalizedEntityNotFoundException;
import ua.unsober.backend.repository.DepartmentRepository;

@Component
@RequiredArgsConstructor
public class SpecialityRequestMapper {
    private final DepartmentRepository departmentRepository;
    private final LocalizedEntityNotFoundException notFound;

    public Speciality toEntity(SpecialityRequestDto dto) {
        if (dto == null) return null;
        Speciality entity = Speciality.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();

        java.util.UUID departmentId = dto.getDepartmentId();
        if (departmentId != null) {
            entity.setDepartment(departmentRepository.findById(departmentId).orElseThrow(() ->
                    notFound.forEntity("error.department.notfound", departmentId)));
        }
        return entity;
    }
}
