package ua.unsober.backend.feature.department;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import ua.unsober.backend.feature.faculty.Faculty;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"faculty_id", "name"}))
public class Department {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="faculty_id", nullable=false)
    private Faculty faculty;

    @NotBlank
    @Size(max=100)
    @Column(length=100, nullable=false)
    private String name;

    @Size(max=1000)
    @Column(length=1000)
    private String description;

    @Version
    @Column(nullable=false)
    private Integer rowVersion;
}