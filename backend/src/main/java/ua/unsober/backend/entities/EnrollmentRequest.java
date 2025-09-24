package ua.unsober.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class EnrollmentRequest {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="student_id", nullable=false)
    private Student student;

    @NotNull
    @ManyToOne
    @JoinColumn(name="course_id", nullable=false)
    private Course course;

    @NotBlank
    @Size(max=3000)
    @Column(length=3000, nullable=false)
    private String reason;

    @NotBlank
    @Size(max=20)
    @Column(length=20, nullable=false)
    private String status;

    @Version
    @Column(nullable=false)
    private Integer rowVersion;

    @CreationTimestamp
    @Column(name="created_at", nullable=false, updatable=false)
    private Instant createdAt;
}
