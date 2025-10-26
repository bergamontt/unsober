package ua.unsober.backend.feature.user;

import java.util.UUID;

public interface UserService {
    UserResponseDto getById(UUID id);
    UserResponseDto getByEmail(String email);
    UserResponseDto create(UserRequestDto dto);
    UserResponseDto update(UUID id, UserRequestDto dto);
    void delete (UUID id);
}
