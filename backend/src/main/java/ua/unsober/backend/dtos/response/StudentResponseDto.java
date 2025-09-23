package ua.unsober.backend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String recordBookNumber;
    private String email;
    private SpecialityResponseDto specialty;
    private Integer studyYear;
}