package ua.unsober.backend.dtos.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequestDto {
    @NotBlank(message = "{student.firstName.required}")
    @Size(max = 100, message = "{student.firstName.size}")
    private String firstName;

    @NotBlank(message = "{student.lastName.required}")
    @Size(max = 100, message = "{student.lastName.size}")
    private String lastName;

    @NotBlank(message = "{student.patronymic.required}")
    @Size(max = 100, message = "{student.patronymic.size}")
    private String patronymic;

    @NotBlank(message = "{student.recordBookNumber.required}")
    @Size(max = 50, message = "{student.recordBookNumber.size}")
    private String recordBookNumber;

    @NotBlank(message = "{student.email.required}")
    @Email(message = "{student.email.invalid}")
    @Size(max = 200, message = "{student.email.size}")
    private String email;

    @NotBlank(message = "{student.password.required}")
    private String password;

    @NotNull(message = "{student.specialtyId.required}")
    private UUID specialtyId;

    @NotNull(message = "{student.studyYear.required}")
    @Positive(message = "{student.studyYear.positive}")
    private Integer studyYear;
}