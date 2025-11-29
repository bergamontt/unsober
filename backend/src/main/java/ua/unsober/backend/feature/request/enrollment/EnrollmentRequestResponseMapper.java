package ua.unsober.backend.feature.request.enrollment;

import org.mapstruct.Mapper;
import ua.unsober.backend.feature.student.StudentResponseMapper;

@Mapper(componentModel = "spring", uses = {StudentResponseMapper.class})
public interface EnrollmentRequestResponseMapper {
    EnrollmentRequestResponseDto toDto(EnrollmentRequest enrollmentRequest);
}
