package ua.unsober.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @NotNull
    @Min(1)
    @Max(7)
    @Column(name="class_number", nullable=false)
    private Integer classNumber;

    @Size(max=10)
    @Column(length=10)
    private String location;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Version
    @Column(nullable=false)
    private Integer rowVersion;
}