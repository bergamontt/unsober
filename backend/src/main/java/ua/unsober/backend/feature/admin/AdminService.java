package ua.unsober.backend.feature.admin;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    AdminResponseDto create(AdminRequestDto dto);
    List<AdminResponseDto> getAll();
    AdminResponseDto getById(UUID id);
    AdminResponseDto update(UUID id, AdminRequestDto dto);
    void delete(UUID id);
    AdminResponseDto getByEmail(String email);
}