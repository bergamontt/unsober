package ua.unsober.backend.feature.department;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentResponseMapper {
    DepartmentResponseDto toDto(Department department);
}
