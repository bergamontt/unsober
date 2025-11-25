package ua.unsober.backend.feature.request.enrollment;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnrollmentRequestResponseMapper {
    EnrollmentRequestResponseDto toDto(EnrollmentRequest enrollmentRequest);
}
