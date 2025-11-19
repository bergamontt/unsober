package ua.unsober.backend.feature.student;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import ua.unsober.backend.feature.speciality.Speciality;
import ua.unsober.backend.feature.user.User;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotNull
    @OneToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @NotBlank
    @Size(max=50)
    @Column(name="record_book_number", length=50, nullable=false)
    private String recordBookNumber;

    @NotNull
    @ManyToOne
    @JoinColumn(name="specialty_id", nullable=false)
    private Speciality speciality;

    @NotNull
    @Positive
    @Column(name="study_year", nullable=false)
    private Integer studyYear;

    @Version
    @Column(nullable=false)
    private Integer rowVersion;
}