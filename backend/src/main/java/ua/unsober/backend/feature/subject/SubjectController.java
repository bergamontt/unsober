package ua.unsober.backend.feature.subject;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping
    public SubjectResponseDto create(@Valid @RequestBody SubjectRequestDto dto) {
        return subjectService.create(dto);
    }

    @GetMapping
    public Page<SubjectResponseDto> getAll(
            @ModelAttribute SubjectFilterDto filters,
            Pageable pageable
    ) {
        return subjectService.getAll(filters, pageable);
    }

    @GetMapping("/{id}")
    public SubjectResponseDto getById(@PathVariable UUID id) {
        return subjectService.getById(id);
    }

    @PatchMapping("/{id}")
    public SubjectResponseDto update(
            @PathVariable UUID id,
            @RequestBody SubjectRequestDto dto) {
        return subjectService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        subjectService.delete(id);
    }
}
