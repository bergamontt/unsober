package ua.unsober.backend.feature.studentEnrollment;

import org.mapstruct.Mapper;
import ua.unsober.backend.feature.student.StudentResponseMapper;

@Mapper(componentModel = "spring", uses = {StudentResponseMapper.class})
public interface StudentEnrollmentResponseMapper {
    StudentEnrollmentResponseDto toDto(StudentEnrollment enrollment);
}
