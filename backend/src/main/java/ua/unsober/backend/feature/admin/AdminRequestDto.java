package ua.unsober.backend.feature.admin;

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
    @NotBlank(message = "{student.firstName.required}")
    @Size(max = 100, message = "{student.firstName.size}")
    private String firstName;

    @NotBlank(message = "{student.lastName.required}")
    @Size(max = 100, message = "{student.lastName.size}")
    private String lastName;

    @NotBlank(message = "{student.patronymic.required}")
    @Size(max = 100, message = "{student.patronymic.size}")
    private String patronymic;

    @NotBlank(message = "{admin.email.required}")
    @Email(message = "{admin.email.invalid}")
    @Size(max = 200, message = "{admin.email.size}")
    private String email;

    @NotBlank(message = "{admin.password.required}")
    private String password;
}