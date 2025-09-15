package ua.unsober.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"subject_id", "course_year"}))
public class Course {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="subject_id", nullable = false)
    private Subject subject;

    @PositiveOrZero
    @Column(name="max_students")
    private Integer maxStudents;

    @NotNull
    @PositiveOrZero
    @Column(name="num_enrolled", nullable=false)
    private Integer numEnrolled;

    @NotNull
    @Column(name="course_year", nullable=false)
    private Integer courseYear;

    @Version
    @Column(nullable=false)
    private Integer rowVersion;

    @CreationTimestamp
    @Column(name="created_at", nullable=false, updatable=false)
    private Instant createdAt;
}