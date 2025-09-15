package ua.unsober.backend.models;

import jakarta.persistence.*;
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
public class Subject {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(length=100, nullable=false)
    private String name;

    @Column(length=5000)
    private String annotation;

    @Column(nullable=false)
    private Double credits;

    @Column(nullable=false)
    private String term;

    @Version
    @Column(nullable=false)
    private Integer rowVersion;

    @CreationTimestamp
    @Column(name="created_at", nullable=false, updatable=false)
    private Instant createdAt;
}
