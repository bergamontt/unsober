package ua.unsober.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
public class Teacher {
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
    @Email
    @Size(max=200)
    @Column(length=200, unique=true, nullable=false)
    private String email;

    @Version
    @Column(nullable=false)
    private Integer rowVersion;
}