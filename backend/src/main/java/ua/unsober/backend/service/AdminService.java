package ua.unsober.backend.service;

import ua.unsober.backend.dtos.request.AdminRequestDto;
import ua.unsober.backend.dtos.response.AdminResponseDto;

import java.util.UUID;

public interface AdminService {
    AdminResponseDto create(AdminRequestDto dto);
    AdminResponseDto getById(UUID id);
    AdminResponseDto update(UUID id, AdminRequestDto dto);
    void delete(UUID id);
}