package ua.unsober.backend.feature.teacher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherResponseDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
}