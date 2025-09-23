package ua.unsober.backend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpecialityResponseDto {
    private UUID id;
    private DepartmentResponseDto department;
    private String name;
    private String description;
}