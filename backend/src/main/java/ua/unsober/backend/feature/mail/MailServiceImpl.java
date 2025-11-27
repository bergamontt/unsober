package ua.unsober.backend.feature.mail;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ua.unsober.backend.feature.studentenrollment.StudentEnrollment;

@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final MailTemplateLoader templateLoader;

    @Async
    @Override
    public void sendScheduleChangeNotification(StudentEnrollment enrollment) {
        String email = enrollment.getStudent().getUser().getEmail();
        String name = enrollment.getStudent().getUser().getFirstName();
        String courseName = enrollment.getCourse().getSubject().getName();

        String text = templateLoader.getScheduleChangeText(name, courseName);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Зміна розкладу курсу: " + courseName);
        message.setText(text);

        mailSender.send(message);
    }
}
