package ua.unsober.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class Admin {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotBlank
    @Email
    @Size(max=200)
    @Column(length=200, unique=true, nullable=false)
    private String email;

    @NotBlank
    @Size(min=60, max=60)
    @Column(name="password_hash", length=60, nullable=false)
    private String passwordHash;

    @Version
    @Column(nullable=false)
    private Integer rowVersion;

    @CreationTimestamp
    @Column(name="created_at", nullable=false, updatable=false)
    private Instant createdAt;
}