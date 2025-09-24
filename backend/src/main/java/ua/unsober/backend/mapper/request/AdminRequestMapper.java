package ua.unsober.backend.mapper.request;

import org.springframework.stereotype.Component;
import ua.unsober.backend.dtos.request.AdminRequestDto;
import ua.unsober.backend.entities.Admin;

@Component
public class AdminRequestMapper {
    public Admin toEntity(AdminRequestDto dto){
        return Admin.builder()
                .email(dto.getEmail())
                .passwordHash(dto.getPassword())
                .build();
    }
}
