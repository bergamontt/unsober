package ua.unsober.backend.mapper.response;

import org.mapstruct.Mapper;
import ua.unsober.backend.dtos.response.DepartmentResponseDto;
import ua.unsober.backend.entities.Department;

@Mapper(componentModel = "spring")
public interface DepartmentResponseMapper {
    DepartmentResponseDto toDto(Department department);
}
