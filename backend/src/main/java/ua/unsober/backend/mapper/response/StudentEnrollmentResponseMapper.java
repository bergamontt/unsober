package ua.unsober.backend.mapper.response;

import org.mapstruct.Mapper;
import ua.unsober.backend.dtos.response.StudentEnrollmentResponseDto;
import ua.unsober.backend.entities.StudentEnrollment;

@Mapper(componentModel = "spring")
public interface StudentEnrollmentResponseMapper {
    StudentEnrollmentResponseDto toDto(StudentEnrollment enrollment);
}
