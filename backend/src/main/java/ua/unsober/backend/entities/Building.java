package ua.unsober.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Building {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotBlank
    @Size(max = 100)
    @Column(length = 100, nullable=false, unique=true)
    private String name;

    @NotBlank
    @Size(max = 200)
    @Column(length = 200, nullable = false, unique=true)
    private String address;

    @NotNull
    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    @Digits(integer=2, fraction=8)
    @Column(nullable = false, precision = 10, scale = 8)
    private BigDecimal latitude;

    @NotNull
    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    @Digits(integer=3, fraction=8)
    @Column(nullable = false, precision = 11, scale = 8)
    private BigDecimal longitude;

    @Version
    @Column(nullable = false)
    private Integer rowVersion;
}
