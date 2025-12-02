package ua.unsober.backend.feature.mail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import ua.unsober.backend.feature.course.Course;
import ua.unsober.backend.feature.student.Student;
import ua.unsober.backend.feature.studentenrollment.StudentEnrollment;
import ua.unsober.backend.feature.subject.Subject;
import ua.unsober.backend.feature.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailServiceUnitTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private MailTemplateLoader templateLoader;

    @InjectMocks
    private MailServiceImpl mailService;

    private static final String STUDENT_NAME = "Name";
    private static final String STUDENT_EMAIL = "test@example.com";
    private static final String COURSE_NAME = "Course";
    private static final String SCHEDULE_CHANGE_TEXT = "Вітаємо Name, розклад курсу Course змінився.";
    private static final String EXPECTED_SUBJECT = "Зміна розкладу курсу: Course";

    @Test
    void shouldSendScheduleChangeNotificationWithCorrectData() {
        StudentEnrollment enrollment = enrollment();
        when(templateLoader.getScheduleChangeText(STUDENT_NAME, COURSE_NAME))
                .thenReturn(SCHEDULE_CHANGE_TEXT);
        mailService.sendScheduleChangeNotification(enrollment);
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(captor.capture());
        SimpleMailMessage sentMessage = captor.getValue();
        assertNotNull(sentMessage.getTo());
        assertEquals(STUDENT_EMAIL, sentMessage.getTo()[0]);
        assertEquals(EXPECTED_SUBJECT, sentMessage.getSubject());
        assertEquals(SCHEDULE_CHANGE_TEXT, sentMessage.getText());
    }

    private User user() {
        return User.builder()
                .firstName(STUDENT_NAME)
                .email(STUDENT_EMAIL)
                .build();
    }

    private Student student() {
        return Student.builder()
                .user(user())
                .build();
    }

    private Subject subject() {
        return Subject.builder()
                .name(COURSE_NAME)
                .build();
    }

    private Course course() {
        return Course.builder()
                .subject(subject())
                .build();
    }

    private StudentEnrollment enrollment() {
        return StudentEnrollment.builder()
                .student(student())
                .course(course())
                .build();
    }

}
