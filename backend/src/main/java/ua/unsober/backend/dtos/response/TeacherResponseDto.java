package ua.unsober.backend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherResponseDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
}