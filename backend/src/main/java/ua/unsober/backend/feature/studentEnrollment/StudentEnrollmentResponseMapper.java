package ua.unsober.backend.feature.studentEnrollment;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentEnrollmentResponseMapper {
    StudentEnrollmentResponseDto toDto(StudentEnrollment enrollment);
}
