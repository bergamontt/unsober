package ua.unsober.backend.mapper.response;

import org.mapstruct.Mapper;
import ua.unsober.backend.dtos.response.EnrollmentRequestResponseDto;
import ua.unsober.backend.entities.EnrollmentRequest;

@Mapper(componentModel = "spring")
public interface EnrollmentRequestResponseMapper {
    EnrollmentRequestResponseDto toDto(EnrollmentRequest enrollmentRequest);
}
