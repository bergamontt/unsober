package ua.unsober.backend.feature.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.unsober.backend.common.enums.StudentStatus;
import ua.unsober.backend.feature.speciality.SpecialityResponseDto;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentResponseDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String recordBookNumber;
    private String email;
    private SpecialityResponseDto speciality;
    private Integer studyYear;
    private StudentStatus status;
}