package ua.unsober.backend.feature.monitor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.unsober.backend.feature.course.Course;
import ua.unsober.backend.feature.course.CourseRepository;
import ua.unsober.backend.feature.courseclass.CourseClass;
import ua.unsober.backend.feature.courseclass.CourseClassRepository;
import ua.unsober.backend.feature.coursegroup.CourseGroup;
import ua.unsober.backend.feature.coursegroup.CourseGroupRepository;
import ua.unsober.backend.feature.mail.MailService;
import ua.unsober.backend.feature.student.Student;
import ua.unsober.backend.feature.studentenrollment.StudentEnrollment;
import ua.unsober.backend.feature.subject.Subject;
import ua.unsober.backend.feature.user.User;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleMonitorServiceUnitTest {

    @Mock
    private MailService mailService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseClassRepository courseClassRepository;

    @Mock
    private CourseGroupRepository courseGroupRepository;

    @InjectMocks
    private ScheduleMonitorService scheduleMonitorService;

    @Test
    void checkCourseScheduleChangesShouldNotNotifyStudentsWhenNothingChanged() {
        Course course = course();
        CourseGroup group = group(course);
        CourseClass courseClass = courseClass(group);
        StudentEnrollment enrollment = enrollment(course);
        course.setEnrollments(List.of(enrollment));

        when(courseRepository.findAll()).thenReturn(List.of(course));
        when(courseGroupRepository.findByCourseId(course.getId())).thenReturn(List.of(group));
        when(courseClassRepository.getAllByGroupId(group.getId())).thenReturn(List.of(courseClass));

        scheduleMonitorService.checkCourseScheduleChanges();
        verify(mailService, never()).sendScheduleChangeNotification(any());
    }

    @Test
    void checkCourseScheduleChangesShouldNotifyOnlyOnChange() {
        Course course = course();
        CourseGroup group = group(course);
        CourseClass courseClass = courseClass(group);
        StudentEnrollment enrollment = enrollment(course);
        course.setEnrollments(List.of(enrollment));

        when(courseRepository.findAll()).thenReturn(List.of(course));
        when(courseGroupRepository.findByCourseId(course.getId())).thenReturn(List.of(group));
        when(courseClassRepository.getAllByGroupId(group.getId())).thenReturn(List.of(courseClass));

        scheduleMonitorService.checkCourseScheduleChanges();
        verify(mailService, never()).sendScheduleChangeNotification(any());

        courseClass.setRowVersion(2);
        scheduleMonitorService.checkCourseScheduleChanges();

        ArgumentCaptor<StudentEnrollment> captor = ArgumentCaptor.forClass(StudentEnrollment.class);
        verify(mailService, times(1)).sendScheduleChangeNotification(captor.capture());
        assertEquals(enrollment, captor.getValue());
    }

    private User user() {
        return User.builder()
                .id(UUID.randomUUID())
                .firstName("Name")
                .email("test@example.com")
                .rowVersion(1)
                .build();
    }

    private Student student() {
        return Student.builder()
                .id(UUID.randomUUID())
                .user(user())
                .rowVersion(1)
                .build();
    }

    private Subject subject() {
        return Subject.builder()
                .id(UUID.randomUUID())
                .name("Course")
                .rowVersion(1)
                .build();
    }

    private Course course() {
        return Course.builder()
                .id(UUID.randomUUID())
                .subject(subject())
                .rowVersion(1)
                .build();
    }

    private CourseGroup group(Course course) {
        return CourseGroup.builder()
                .id(UUID.randomUUID())
                .course(course)
                .rowVersion(1)
                .build();
    }

    private CourseClass courseClass(CourseGroup group) {
        return CourseClass.builder()
                .id(UUID.randomUUID())
                .group(group)
                .rowVersion(1)
                .build();
    }

    private StudentEnrollment enrollment(Course course) {
        return StudentEnrollment.builder()
                .id(UUID.randomUUID())
                .student(student())
                .course(course)
                .rowVersion(1)
                .build();
    }

}
