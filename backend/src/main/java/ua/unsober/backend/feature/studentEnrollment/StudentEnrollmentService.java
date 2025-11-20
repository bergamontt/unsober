package ua.unsober.backend.feature.studentEnrollment;

import java.util.List;
import java.util.UUID;

public interface StudentEnrollmentService {
    StudentEnrollmentResponseDto create(StudentEnrollmentRequestDto dto);
    List<StudentEnrollmentResponseDto> getAll();
    StudentEnrollmentResponseDto getById(UUID id);
    List<StudentEnrollmentResponseDto> getAllByStudentId(UUID studentId);
    StudentEnrollmentResponseDto update(UUID id, StudentEnrollmentRequestDto dto);
    void delete(UUID id);
    StudentEnrollmentResponseDto enrollSelf(UUID courseId);
    StudentEnrollmentResponseDto changeGroup(UUID enrollmentId, UUID groupId);
}
