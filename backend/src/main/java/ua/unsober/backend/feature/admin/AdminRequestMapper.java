package ua.unsober.backend.feature.admin;

import org.springframework.stereotype.Component;

@Component
public class AdminRequestMapper {
    public Admin toEntity(AdminRequestDto dto){
        return Admin.builder()
                .email(dto.getEmail())
                .passwordHash(dto.getPassword())
                .build();
    }
}
