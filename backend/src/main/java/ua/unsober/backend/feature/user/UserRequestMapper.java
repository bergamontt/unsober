package ua.unsober.backend.feature.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRequestMapper {
    private final PasswordEncoder passwordEncoder;

    public User toEntity(UserRequestDto dto) {
        if (dto == null)
            return null;
        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .patronymic(dto.getPatronymic())
                .role(dto.getRole())
                .email(dto.getEmail())
                .passwordHash(dto.getPassword() != null ? passwordEncoder.encode(dto.getPassword()) : null)
                .build();
    }
}
