package ua.unsober.backend.feature.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import ua.unsober.backend.common.enums.Role;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_account")
public class User {
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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

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
}