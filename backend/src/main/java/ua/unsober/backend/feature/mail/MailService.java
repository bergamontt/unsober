package ua.unsober.backend.feature.mail;

import ua.unsober.backend.common.enums.EnrollmentStage;
import ua.unsober.backend.feature.student.Student;
import ua.unsober.backend.feature.studentenrollment.StudentEnrollment;

public interface MailService {
    void sendScheduleChangeNotification(StudentEnrollment enrollment);
    void sendStageChangeNotification(Student student, EnrollmentStage enrollmentStage);
}