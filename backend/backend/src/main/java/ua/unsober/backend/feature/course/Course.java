package ua.unsober.backend.feature.course;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import ua.unsober.backend.feature.student.Student;
import ua.unsober.backend.feature.subject.Subject;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @ManyToMany(mappedBy = "courses")
    private Set<Student> students = new HashSet<>();
}