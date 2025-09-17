package ua.unsober.backend.dtos.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import ua.unsober.backend.models.Department;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialtyRequestDto {
    @NotNull
    private UUID departmentId;

    @NotBlank
    @Size(max=100)
    private String name;

    @NotBlank
    @Size(max=1000)
    private String description;
}