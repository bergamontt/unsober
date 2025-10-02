package ua.unsober.backend.feature.speciality;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import ua.unsober.backend.feature.department.Department;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Speciality {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="department_id", nullable=false)
    private Department department;

    @NotBlank
    @Size(max=100)
    @Column(length=100, nullable=false)
    private String name;

    @NotBlank
    @Size(max=1000)
    @Column(length=1000, nullable=false)
    private String description;

    @Version
    @Column(nullable=false)
    private Integer rowVersion;
}