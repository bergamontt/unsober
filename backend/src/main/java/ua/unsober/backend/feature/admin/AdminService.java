package ua.unsober.backend.feature.admin;

import java.util.UUID;

public interface AdminService {
    AdminResponseDto create(AdminRequestDto dto);
    AdminResponseDto getById(UUID id);
    AdminResponseDto update(UUID id, AdminRequestDto dto);
    void delete(UUID id);
}