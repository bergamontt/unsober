package ua.unsober.backend.feature.studentenrollment;

import java.util.List;
import java.util.UUID;

public interface StudentEnrollmentService {
    StudentEnrollmentResponseDto create(StudentEnrollmentRequestDto dto);
    List<StudentEnrollmentResponseDto> getAll();
    StudentEnrollmentResponseDto getById(UUID id);
    List<StudentEnrollmentResponseDto> getAllByStudentId(UUID studentId);
    List<StudentEnrollmentResponseDto> getAllByStudentIdAndYear(UUID studentId, Integer year);
    List<StudentEnrollmentResponseDto> getAllByCourseId(UUID courseId);
    List<Integer> getAllYearsByStudentId(UUID studentId);
    boolean existsByStudentAndCourseId(UUID studentId, UUID courseId);
    StudentEnrollmentResponseDto update(UUID id, StudentEnrollmentRequestDto dto);
    void delete(UUID id);
    StudentEnrollmentResponseDto enrollSelf(UUID courseId);
    StudentEnrollmentResponseDto changeGroup(UUID enrollmentId, UUID groupId);
}
