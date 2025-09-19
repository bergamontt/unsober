package ua.unsober.backend.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRequestDto {
    @NotBlank(message = "{admin.email.required}")
    @Email(message = "{admin.email.invalid}")
    @Size(max = 200, message = "{admin.email.size}")
    private String email;

    @NotBlank(message = "{admin.password.required}")
    private String password;
}