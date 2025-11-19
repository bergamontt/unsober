package ua.unsober.backend.feature.terms;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.common.enums.Term;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/term-info")
@RequiredArgsConstructor
public class TermInfoController {
    private final TermInfoService termInfoService;

    @PostMapping
    public TermInfoResponseDto create(@Valid @RequestBody TermInfoRequestDto dto) {
        return termInfoService.create(dto);
    }

    @GetMapping
    public List<TermInfoResponseDto> getAll() {
        return termInfoService.getAll();
    }

    @GetMapping("/uuid/{id}")
    public TermInfoResponseDto getById(@PathVariable UUID id) {
        return termInfoService.getById(id);
    }

    @GetMapping("/year-and-term/")
    public TermInfoResponseDto getByYearAnTerm(@RequestParam Integer year, @RequestParam Term term) {
        return termInfoService.getByYearAndTerm(year, term);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        termInfoService.deleteById(id);
    }
}
