package ua.unsober.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CourseClass {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private CourseGroup group;

    @Column(length=100, nullable=false)
    private String title;

    @Column(length=100, nullable=false)
    private String type;

    @Column(name="weeks_list", length=100, nullable=false)
    private String weeksList;

    @Column(name="week_day", length=10, nullable=false)
    private String weekDay;

    @Column(name="class_number")
    private Integer classNumber;

    @Column(length=10, nullable=false)
    private String location;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Version
    @Column(nullable=false)
    private Integer rowVersion;

    @CreationTimestamp
    @Column(name="created_at", nullable=false, updatable=false)
    private Instant createdAt;
}