package ua.unsober.backend.feature.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
