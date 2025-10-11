package ua.unsober.backend.feature.department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.unsober.backend.feature.faculty.FacultyResponseDto;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentResponseDto {
    private UUID id;
    private FacultyResponseDto faculty;
    private String name;
    private String description;
}
