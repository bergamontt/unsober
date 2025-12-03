package ua.unsober.backend.feature.request.enrollment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.common.enums.RequestStatus;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/enrollment-request")
@RequiredArgsConstructor
public class EnrollmentRequestController {

    private final EnrollmentRequestService enrollmentRequestService;

    @PostMapping
    public EnrollmentRequestResponseDto create(@Valid @RequestBody EnrollmentRequestRequestDto dto) {
        return enrollmentRequestService.create(dto);
    }

    @GetMapping
    public List<EnrollmentRequestResponseDto> getAll(@ModelAttribute EnrollmentRequestFilterDto filters) {
        return enrollmentRequestService.getAll(filters);
    }

    @GetMapping("/status/{status}")
    public List<EnrollmentRequestResponseDto> getAllWithStatus(@PathVariable RequestStatus status) {
        return enrollmentRequestService.getAllWithStatus(status);
    }

    @GetMapping("/student/{studentId}")
    public List<EnrollmentRequestResponseDto> getAllByStudentId(@PathVariable UUID studentId) {
        return enrollmentRequestService.getAllByStudentId(studentId);
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

    @PatchMapping("/update-status/{id}")
    public EnrollmentRequestResponseDto updateStatus(
            @PathVariable UUID id,
            @RequestParam RequestStatus status) {
        return enrollmentRequestService.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        enrollmentRequestService.delete(id);
    }
}

