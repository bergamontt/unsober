package ua.unsober.backend.feature.admin;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import ua.unsober.backend.feature.user.User;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotNull
    @OneToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Version
    @Column(nullable=false)
    private Integer rowVersion;
}