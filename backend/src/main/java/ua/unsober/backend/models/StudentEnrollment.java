package ua.unsober.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"}))
public class StudentEnrollment {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn(name="student_id", nullable=false)
    private Student student;

    @ManyToOne
    @JoinColumn(name="course_id", nullable=false)
    private Course course;

    @ManyToOne
    @JoinColumn(name="group_id")
    private CourseGroup group;

    @Column(length=30, nullable=false)
    private String status;

    @Column(nullable=false)
    private Integer enrollmentYear;

    @Version
    @Column(nullable=false)
    private Integer rowVersion;

    @CreationTimestamp
    @Column(name="created_at", nullable=false, updatable=false)
    private Instant createdAt;
}
