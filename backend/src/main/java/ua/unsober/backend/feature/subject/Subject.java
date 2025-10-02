package ua.unsober.backend.feature.subject;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import ua.unsober.backend.common.enums.Term;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subject {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotBlank
    @Size(max=100)
    @Column(length=100, nullable=false)
    private String name;

    @Size(max=5000)
    @Column(length=5000)
    private String annotation;

    @NotNull
    @Digits(integer=2, fraction=1)
    @Column(nullable=false, precision=3, scale=1)
    private BigDecimal credits;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Term term;

    @Version
    @Column(nullable=false)
    private Integer rowVersion;
}
