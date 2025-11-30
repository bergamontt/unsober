package ua.unsober.backend.feature.monitor;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.unsober.backend.feature.course.Course;
import ua.unsober.backend.feature.course.CourseRepository;
import ua.unsober.backend.feature.courseclass.CourseClass;
import ua.unsober.backend.feature.courseclass.CourseClassRepository;
import ua.unsober.backend.feature.coursegroup.CourseGroup;
import ua.unsober.backend.feature.coursegroup.CourseGroupRepository;
import ua.unsober.backend.feature.mail.MailService;
import ua.unsober.backend.feature.studentenrollment.StudentEnrollment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ScheduleMonitorService {
    private final MailService mailService;
    private final CourseRepository courseRepository;
    private final CourseClassRepository courseClassRepository;
    private final CourseGroupRepository courseGroupRepository;
    private final Map<UUID, Integer> scheduleVersion = new HashMap<>();

    @Transactional
    @Scheduled(fixedRate = 4 * 60 * 60 * 1000)
    public void checkCourseScheduleChanges() {
        List<Course> allCourses = courseRepository.findAll();
        for (Course course : allCourses) {
            int checkSum = scheduleCheckSum(course);
            Integer previous = scheduleVersion.get(course.getId());
            if (previous != null && !previous.equals(checkSum))
                notifyStudents(course);
            scheduleVersion.put(course.getId(), checkSum);
        }
    }

    private int scheduleCheckSum(Course course) {
        int sum = course.getRowVersion();
        for (CourseGroup group : courseGroupRepository.findByCourseId(course.getId()))
            for (CourseClass courseClass : courseClassRepository.getAllByGroupId(group.getId()))
                sum += courseClass.getRowVersion();
        return sum;
    }

    private void notifyStudents(Course course) {
        for (StudentEnrollment enrollment : course.getEnrollments())
            mailService.sendScheduleChangeNotification(enrollment);
    }

}