package ua.unsober.backend.feature.courseclass;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import ua.unsober.backend.feature.building.Building;
import ua.unsober.backend.feature.course.Course;
import ua.unsober.backend.feature.coursegroup.CourseGroup;
import ua.unsober.backend.feature.teacher.Teacher;
import ua.unsober.backend.common.enums.ClassType;
import ua.unsober.backend.common.enums.WeekDay;

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

    @ManyToOne
    @JoinColumn(name = "group_id")
    private CourseGroup group;

    @NotBlank
    @Size(max=100)
    @Column(length=100, nullable=false)
    private String title;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClassType type;

    @NotBlank
    @Size(max=100)
    @Column(name="weeks_list", length=100, nullable=false)
    private String weeksList;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WeekDay weekDay;

    @NotNull
    @Min(1)
    @Max(7)
    @Column(name="class_number", nullable=false)
    private Integer classNumber;

    @Size(max=100)
    @Column(length=100)
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