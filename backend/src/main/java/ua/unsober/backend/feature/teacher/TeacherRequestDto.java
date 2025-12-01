package ua.unsober.backend.feature.teacher;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherRequestDto {
    @NotBlank(message = "{teacher.firstName.required}")
    @Size(max = 100, message = "{teacher.firstName.size}")
    private String firstName;

    @NotBlank(message = "{teacher.lastName.required}")
    @Size(max = 100, message = "{teacher.lastName.size}")
    private String lastName;

    @NotBlank(message = "{teacher.patronymic.required}")
    @Size(max = 100, message = "{teacher.patronymic.size}")
    private String patronymic;

    @NotBlank(message = "{teacher.email.required}")
    @Email(message = "{teacher.email.invalid}")
    @Size(max = 200, message = "{teacher.email.size}")
    private String email;
}