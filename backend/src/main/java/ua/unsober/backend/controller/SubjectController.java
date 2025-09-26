package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.SubjectRequestDto;
import ua.unsober.backend.dtos.response.SubjectResponseDto;
import ua.unsober.backend.service.SubjectService;

import java.util.UUID;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping
    public SubjectResponseDto create(@Valid @RequestBody SubjectRequestDto dto) {
        return subjectService.create(dto);
    }

    @GetMapping
    public Page<SubjectResponseDto> getAll(Pageable pageable) {
        return subjectService.getAll(pageable);
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
