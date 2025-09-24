package ua.unsober.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class Faculty {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotBlank
    @Size(max=100)
    @Column(unique=true, length=100, nullable=false)
    private String name;

    @Size(max=1000)
    @Column(length=1000)
    private String description;

    @Version
    @Column(name="row_version", nullable=false)
    private Integer rowVersion;
}