package ua.unsober.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
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
public class Faculty {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(unique=true, length=100, nullable=false)
    private String name;

    @Column(length=1000)
    private String description;

    @Version
    @Column(name="row_version", nullable=false)
    private Integer rowVersion;

    @CreationTimestamp
    @Column(name="created_at", nullable=false, updatable=false)
    private Instant createdAt;
}