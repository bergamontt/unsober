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
import ua.unsober.backend.enums.RequestStatus;

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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status;

    @Version
    @Column(nullable=false)
    private Integer rowVersion;

    @CreationTimestamp
    @Column(name="created_at", nullable=false, updatable=false)
    private Instant createdAt;
}
