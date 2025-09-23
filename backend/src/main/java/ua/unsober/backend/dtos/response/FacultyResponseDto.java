package ua.unsober.backend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacultyResponseDto {
    private UUID id;
    private String name;
    private String description;
}