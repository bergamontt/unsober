package ua.unsober.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotNull
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "group_id")
    private CourseGroup group;

    @NotBlank
    @Size(max=100)
    @Column(length=100, nullable=false)
    private String title;

    @NotBlank
    @Size(max=100)
    @Column(length=100, nullable=false)
    private String type;

    @NotBlank
    @Size(max=100)
    @Column(name="weeks_list", length=100, nullable=false)
    private String weeksList;

    @NotBlank
    @Size(max=10)
    @Column(name="week_day", length=10, nullable=false)
    private String weekDay;

    @Min(1)
    @Max(7)
    @Column(name="class_number")
    private Integer classNumber;

    @Size(max=10)
    @Column(length=10)
    private String location;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Version
    @Column(nullable=false)
    private Integer rowVersion;

    @CreationTimestamp
    @Column(name="created_at", nullable=false, updatable=false)
    private Instant createdAt;
}