package ua.unsober.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import ua.unsober.backend.enums.Recommendation;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    private Speciality speciality;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Recommendation recommendation;

    @Version
    @Column(nullable=false)
    private Integer rowVersion;
}