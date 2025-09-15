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
public class Student {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name="first_name", length=100, nullable=false)
    private String firstName;

    @Column(name="last_name", length=100, nullable=false)
    private String lastName;

    @Column(name="patronymic", length=100, nullable=false)
    private String patronymic;

    @Column(name="record_book_number", length=50, nullable=false)
    private String recordBookNumber;

    @Column(length=200, unique=true, nullable=false)
    private String email;

    @Column(name="password_hash", length=60, nullable=false)
    private String passwordHash;

    @ManyToOne
    @JoinColumn(name="specialty_id", nullable=false)
    private Specialty specialty;

    @Column(name="study_year", nullable=false)
    private Integer studyYear;

    @Version
    @Column(nullable=false)
    private Integer rowVersion;

    @CreationTimestamp
    @Column(name="created_at", nullable=false, updatable=false)
    private Instant createdAt;
}