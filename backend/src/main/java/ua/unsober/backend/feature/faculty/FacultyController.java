package ua.unsober.backend.feature.faculty;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/faculty")
@RequiredArgsConstructor
public class FacultyController {

    private final FacultyService facultyService;

    @PostMapping
    public FacultyResponseDto create(@Valid @RequestBody FacultyRequestDto dto) {
        return facultyService.create(dto);
    }

    @GetMapping
    public List<FacultyResponseDto> getAll() {
        return facultyService.getAll();
    }

    @GetMapping("/{id}")
    public FacultyResponseDto getById(@PathVariable UUID id) {
        return facultyService.getById(id);
    }

    @PatchMapping("/{id}")
    public FacultyResponseDto update(
            @PathVariable UUID id,
            @RequestBody FacultyRequestDto dto) {
        return facultyService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        facultyService.delete(id);
    }
}


