package ua.unsober.backend.feature.studentenrollment;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.unsober.backend.common.exceptions.CourseFullException;
import ua.unsober.backend.common.exceptions.LocalizedCourseFullExceptionFactory;
import ua.unsober.backend.feature.course.Course;
import ua.unsober.backend.feature.course.CourseRepository;
import ua.unsober.backend.feature.coursegroup.CourseGroup;
import ua.unsober.backend.feature.coursegroup.CourseGroupRepository;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentEnrollmentServiceImplTest {

    @Mock
    private StudentEnrollmentRepository studentEnrollmentRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private CourseGroupRepository courseGroupRepository;
    @Mock
    private StudentEnrollmentRequestMapper requestMapper;
    @Mock
    private StudentEnrollmentResponseMapper responseMapper;
    @Mock
    private LocalizedCourseFullExceptionFactory courseFull;

    @InjectMocks
    private StudentEnrollmentServiceImpl service;

    @Test
    void createShouldThrowWhenCourseIsFull() {
        UUID courseId = UUID.randomUUID();
        StudentEnrollmentRequestDto dto = new StudentEnrollmentRequestDto();
        dto.setCourseId(courseId);

        Course fullCourse = Course.builder()
                .id(courseId)
                .numEnrolled(10)
                .maxStudents(10)
                .build();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(fullCourse));
        when(courseFull.get()).thenReturn(new CourseFullException("course is full"));

        assertThrows(RuntimeException.class, () -> service.create(dto));
        verify(studentEnrollmentRepository, never()).save(any());
    }

    @Test
    void createShouldIncreaseNumEnrolledAndReturnDto() {
        UUID courseId = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();

        StudentEnrollmentRequestDto dto = new StudentEnrollmentRequestDto();
        dto.setCourseId(courseId);
        dto.setGroupId(groupId);

        Course course = Course.builder()
                .id(courseId)
                .numEnrolled(5)
                .maxStudents(10)
                .build();

        CourseGroup group = CourseGroup.builder()
                .id(groupId)
                .numEnrolled(3)
                .maxStudents(10)
                .build();

        StudentEnrollment enrollment = new StudentEnrollment();
        enrollment.setId(UUID.randomUUID());

        StudentEnrollmentResponseDto response = StudentEnrollmentResponseDto.builder()
                .id(enrollment.getId())
                .build();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(courseGroupRepository.findById(groupId)).thenReturn(Optional.of(group));
        when(requestMapper.toEntity(dto)).thenReturn(enrollment);
        when(studentEnrollmentRepository.save(any(StudentEnrollment.class))).thenReturn(enrollment);
        when(responseMapper.toDto(enrollment)).thenReturn(response);

        StudentEnrollmentResponseDto result = service.create(dto);

        assertEquals(response.getId(), result.getId());
        assertEquals(6, course.getNumEnrolled());
        assertEquals(4, group.getNumEnrolled());
        verify(studentEnrollmentRepository).save(enrollment);
    }

    @Test
    void deleteShouldDecreaseNumEnrolledAndRemoveEnrollment() {
        UUID id = UUID.randomUUID();

        Course course = Course.builder()
                .id(UUID.randomUUID())
                .numEnrolled(5)
                .maxStudents(10)
                .build();

        CourseGroup group = CourseGroup.builder()
                .id(UUID.randomUUID())
                .numEnrolled(3)
                .maxStudents(10)
                .build();

        StudentEnrollment enrollment = new StudentEnrollment();
        enrollment.setId(id);
        enrollment.setCourse(course);
        enrollment.setGroup(group);

        when(studentEnrollmentRepository.findById(id)).thenReturn(Optional.of(enrollment));

        service.delete(id);

        assertEquals(4, course.getNumEnrolled());
        assertEquals(2, group.getNumEnrolled());
        verify(studentEnrollmentRepository).delete(enrollment);
        verify(courseRepository).save(course);
        verify(courseGroupRepository).save(group);
    }
}
