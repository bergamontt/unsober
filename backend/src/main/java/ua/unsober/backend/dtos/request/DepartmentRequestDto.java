package ua.unsober.backend.dtos.request;

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
public class DepartmentRequestDto {
    @NotNull(message = "{department.facultyId.required}")
    private UUID facultyId;

    @NotBlank(message = "{department.name.required}")
    @Size(max = 100, message = "{department.name.size}")
    private String name;

    @Size(max = 1000, message = "{department.description.size}")
    private String description;
}