package ua.unsober.backend.feature.speciality;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialityRequestDto {
    @NotNull(message = "{specialty.departmentId.required}")
    private UUID departmentId;

    @NotBlank(message = "{specialty.name.required}")
    @Size(max = 100, message = "{specialty.name.size}")
    private String name;

    @NotBlank(message = "{specialty.description.required}")
    @Size(max = 1000, message = "{specialty.description.size}")
    private String description;
}