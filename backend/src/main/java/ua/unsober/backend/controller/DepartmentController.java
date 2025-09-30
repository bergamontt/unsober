package ua.unsober.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.dtos.request.DepartmentRequestDto;
import ua.unsober.backend.dtos.response.DepartmentResponseDto;
import ua.unsober.backend.service.DepartmentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public DepartmentResponseDto create(@Valid @RequestBody DepartmentRequestDto dto) {
        return departmentService.create(dto);
    }

    @GetMapping
    public List<DepartmentResponseDto> getAll() {
        return departmentService.getAll();
    }

    @GetMapping("/{id}")
    public DepartmentResponseDto getById(@PathVariable UUID id) {
        return departmentService.getById(id);
    }

    @PatchMapping("/{id}")
    public DepartmentResponseDto update(
            @PathVariable UUID id,
            @RequestBody DepartmentRequestDto dto) {
        return departmentService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        departmentService.delete(id);
    }
}

