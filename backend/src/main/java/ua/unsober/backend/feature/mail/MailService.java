package ua.unsober.backend.feature.mail;

import ua.unsober.backend.feature.studentenrollment.StudentEnrollment;

public interface MailService {
    void sendScheduleChangeNotification(StudentEnrollment enrollment);
}