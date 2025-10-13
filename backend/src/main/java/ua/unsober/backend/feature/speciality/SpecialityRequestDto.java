package ua.unsober.backend.feature.speciality;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialityRequestDto {
    private UUID departmentId;

    @NotBlank(message = "{specialty.name.required}")
    @Size(max = 100, message = "{specialty.name.size}")
    private String name;

    @Size(max = 1000, message = "{specialty.description.size}")
    private String description;
}