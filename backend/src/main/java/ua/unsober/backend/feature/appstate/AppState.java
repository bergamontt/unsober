package ua.unsober.backend.feature.appstate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import ua.unsober.backend.common.enums.EnrollmentStage;
import ua.unsober.backend.common.enums.Term;

import java.time.Instant;

@Data
@Entity
public class AppState {
    @Id
    private Integer id = 1;

    @NotNull
    @Column(nullable = false)
    private Integer currentYear;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Term term;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStage enrollmentStage;

    @Version
    @Column(nullable=false)
    private Integer rowVersion;

    @UpdateTimestamp
    @Column(nullable=false)
    private Instant updateTime;
}
