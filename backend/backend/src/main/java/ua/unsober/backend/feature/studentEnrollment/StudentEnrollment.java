package ua.unsober.backend.feature.studentEnrollment;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import ua.unsober.backend.common.enums.EnrollmentStatus;
import ua.unsober.backend.feature.course.Course;
import ua.unsober.backend.feature.courseGroup.CourseGroup;
import ua.unsober.backend.feature.student.Student;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"}))
public class StudentEnrollment {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="student_id", nullable=false)
    private Student student;

    @NotNull
    @ManyToOne
    @JoinColumn(name="course_id", nullable=false)
    private Course course;

    @ManyToOne
    @JoinColumn(name="group_id")
    private CourseGroup group;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStatus status;

    @NotNull
    @Positive
    @Column(nullable=false)
    private Integer enrollmentYear;

    @Version
    @Column(nullable=false)
    private Integer rowVersion;

    @CreationTimestamp
    @Column(name="created_at", nullable=false, updatable=false)
    private Instant createdAt;
}
