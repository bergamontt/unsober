package ua.unsober.backend.mapper.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.dtos.request.DepartmentRequestDto;
import ua.unsober.backend.entities.Department;
import ua.unsober.backend.exceptions.LocalizedEntityNotFoundException;
import ua.unsober.backend.repository.FacultyRepository;

@Component
@RequiredArgsConstructor
public class DepartmentRequestMapper {
    private final FacultyRepository facultyRepository;
    private final LocalizedEntityNotFoundException notFound;

    public Department toEntity(DepartmentRequestDto dto) {
        if (dto == null) return null;
        Department entity = Department.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();

        java.util.UUID facultyId = dto.getFacultyId();
        if (facultyId != null) {
            entity.setFaculty(facultyRepository.findById(facultyId).orElseThrow(() ->
                    notFound.forEntity("error.faculty.notfound", facultyId)));
        }
        return entity;
    }
}
