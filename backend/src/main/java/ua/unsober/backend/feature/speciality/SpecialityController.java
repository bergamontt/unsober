package ua.unsober.backend.feature.speciality;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/speciality")
@RequiredArgsConstructor
public class SpecialityController {

    private final SpecialityService specialityService;

    @PostMapping
    public SpecialityResponseDto create(@Valid @RequestBody SpecialityRequestDto dto) {
        return specialityService.create(dto);
    }

    @GetMapping
    public List<SpecialityResponseDto> getAll(@ModelAttribute SpecialityFilterDto filters) {
        return specialityService.getAll(filters);
    }

    @GetMapping("/{id}")
    public SpecialityResponseDto getById(@PathVariable UUID id) {
        return specialityService.getById(id);
    }

    @PatchMapping("/{id}")
    public SpecialityResponseDto update(
            @PathVariable UUID id,
            @RequestBody SpecialityRequestDto dto) {
        return specialityService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        specialityService.delete(id);
    }

}
