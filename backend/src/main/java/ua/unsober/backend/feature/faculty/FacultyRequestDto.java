package ua.unsober.backend.feature.faculty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacultyRequestDto {
    @NotBlank(message = "{faculty.name.required}")
    @Size(max = 100, message = "{faculty.name.size}")
    private String name;

    @Size(max = 1000, message = "{faculty.description.size}")
    private String description;
}