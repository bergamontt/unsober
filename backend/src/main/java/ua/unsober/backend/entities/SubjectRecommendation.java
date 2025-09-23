package ua.unsober.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class SubjectRecommendation {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "subject_id", nullable=false)
    private Subject subject;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "specialty_id", nullable=false)
    private Specialty specialty;

    @NotBlank
    @Size(max=50)
    @Column(length=50, nullable=false)
    private String recommendation;

    @Version
    @Column(nullable=false)
    private Integer rowVersion;
}