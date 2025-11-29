package ua.unsober.backend.feature.request.withdrawal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.unsober.backend.common.enums.RequestStatus;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/withdrawal-request")
@RequiredArgsConstructor
public class WithdrawalRequestController {

    private final WithdrawalRequestService withdrawalRequestService;

    @PostMapping
    public WithdrawalRequestResponseDto create(@Valid @RequestBody WithdrawalRequestRequestDto dto) {
        return withdrawalRequestService.create(dto);
    }

    @GetMapping
    public List<WithdrawalRequestResponseDto> getAll() {
        return withdrawalRequestService.getAll();
    }

    @GetMapping("/status/{status}")
    public List<WithdrawalRequestResponseDto> getAllWithStatus(@PathVariable RequestStatus status) {
        return withdrawalRequestService.getAllWithStatus(status);
    }

    @GetMapping("/{id}")
    public WithdrawalRequestResponseDto getById(@PathVariable UUID id) {
        return withdrawalRequestService.getById(id);
    }

    @PatchMapping("/{id}")
    public WithdrawalRequestResponseDto update(
            @PathVariable UUID id,
            @RequestBody WithdrawalRequestRequestDto dto) {
        return withdrawalRequestService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        withdrawalRequestService.delete(id);
    }

}
