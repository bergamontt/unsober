package ua.unsober.backend.dtos.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectRequestDto {
    @NotBlank
    @Size(max=100)
    private String name;

    @Size(max=5000)
    private String annotation;

    @NotNull
    @Digits(integer=2, fraction=1)
    private BigDecimal credits;

    @NotNull
    @Size(max=10)
    private String term;
}
