package ua.unsober.backend.feature.request.enrollmentRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/enrollment-request")
@RequiredArgsConstructor
public class EnrollmentRequestController {

    private final EnrollmentRequestService enrollmentRequestService;

    @PostMapping
    public EnrollmentRequestResponseDto create(@Valid @RequestBody EnrollmentRequestRequestDto dto) {
        return enrollmentRequestService.create(dto);
    }

    @GetMapping
    public List<EnrollmentRequestResponseDto> getAll() {
        return enrollmentRequestService.getAll();
    }

    @GetMapping("/{id}")
    public EnrollmentRequestResponseDto getById(@PathVariable UUID id) {
        return enrollmentRequestService.getById(id);
    }

    @PatchMapping("/{id}")
    public EnrollmentRequestResponseDto update(
            @PathVariable UUID id,
            @RequestBody EnrollmentRequestRequestDto dto) {
        return enrollmentRequestService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        enrollmentRequestService.delete(id);
    }
}

