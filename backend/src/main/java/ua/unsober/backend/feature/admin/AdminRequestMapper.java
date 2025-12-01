package ua.unsober.backend.feature.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.unsober.backend.common.enums.Role;
import ua.unsober.backend.feature.user.User;

@Component
@RequiredArgsConstructor
public class AdminRequestMapper {
    private final PasswordEncoder passwordEncoder;

    public Admin toEntity(AdminRequestDto dto) {
        if (dto == null)
            return null;
        User user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .patronymic(dto.getPatronymic())
                .role(Role.ADMIN)
                .email(dto.getEmail())
                .passwordHash(passwordEncoder.encode(dto.getPassword()))
                .build();

        return Admin.builder()
                .user(user)
                .build();
    }

    public AdminRequestDto toDto(Admin entity) {
        if (entity == null) return null;
        User user = entity.getUser();
        return AdminRequestDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .patronymic(user.getPatronymic())
                .email(user.getEmail())
                .password("")
                .build();
    }

}
