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
    @NotBlank
    @Size(max=100)
    private String firstName;

    @NotBlank
    @Size(max=100)
    private String lastName;

    @NotBlank
    @Size(max=100)
    private String patronymic;

    @NotBlank
    @Size(max=50)
    private String recordBookNumber;

    @NotBlank
    @Email
    @Size(max=200)
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private UUID specialtyId;

    @NotNull
    @Positive
    private Integer studyYear;
}