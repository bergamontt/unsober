package ua.unsober.backend.feature.department;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;
import ua.unsober.backend.feature.faculty.FacultyRepository;

@Component
@RequiredArgsConstructor
public class DepartmentRequestMapper {
    private final FacultyRepository facultyRepository;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    public Department toEntity(DepartmentRequestDto dto) {
        if (dto == null) return null;
        Department entity = Department.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();

        java.util.UUID facultyId = dto.getFacultyId();
        if (facultyId != null) {
            entity.setFaculty(facultyRepository.findById(facultyId).orElseThrow(() ->
                    notFound.get("error.faculty.notfound", facultyId)));
        }
        return entity;
    }

    public DepartmentRequestDto toDto(Department entity) {
        if (entity == null) return null;
        return DepartmentRequestDto.builder()
                .facultyId(entity.getFaculty() == null ? null : entity.getFaculty().getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
}
