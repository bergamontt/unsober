package ua.unsober.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotBlank
    @Size(max=100)
    @Column(name="first_name", length=100, nullable=false)
    private String firstName;

    @NotBlank
    @Size(max=100)
    @Column(name="last_name", length=100, nullable=false)
    private String lastName;

    @NotBlank
    @Size(max=100)
    @Column(name="patronymic", length=100, nullable=false)
    private String patronymic;

    @NotBlank
    @Size(max=50)
    @Column(name="record_book_number", length=50, nullable=false)
    private String recordBookNumber;

    @NotBlank
    @Email
    @Size(max=200)
    @Column(length=200, unique=true, nullable=false)
    private String email;

    @NotBlank
    @Size(min=60, max=60)
    @Column(name="password_hash", length=60, nullable=false)
    private String passwordHash;

    @NotNull
    @ManyToOne
    @JoinColumn(name="specialty_id", nullable=false)
    private Specialty specialty;

    @NotNull
    @Positive
    @Column(name="study_year", nullable=false)
    private Integer studyYear;

    @Version
    @Column(nullable=false)
    private Integer rowVersion;
}