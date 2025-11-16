package ua.unsober.backend.feature.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.common.aspects.retry.Retryable;

import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    public AdminResponseDto create(@Valid @RequestBody AdminRequestDto dto) {
        return adminService.create(dto);
    }

    @GetMapping("/{id}")
    public AdminResponseDto getById(@PathVariable UUID id) {
        return adminService.getById(id);
    }

    @PatchMapping("/{id}")
    @Retryable
    public AdminResponseDto update(
            @PathVariable UUID id,
            @RequestBody AdminRequestDto dto) {
        return adminService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        adminService.delete(id);
    }
}

