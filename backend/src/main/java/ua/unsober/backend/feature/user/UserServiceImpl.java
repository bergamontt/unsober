package ua.unsober.backend.feature.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository repository;
    private final UserRequestMapper requestMapper;
    private final UserResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    @Override
    public UserResponseDto getById(UUID id) {
        return responseMapper.toDto(
                repository.findById(id).orElseThrow(() -> {
                    return notFound.get("error.user.notfound", id);
                }));
    }

    @Override
    public UserResponseDto getByEmail(String email) {
        return responseMapper.toDto(
                repository.findByEmail(email).orElseThrow(() -> {
                    return notFound.get("error.user.notfound", email);
                }));
    }

    @Override
    public UserResponseDto create(UserRequestDto dto) {
        return responseMapper.toDto(
                repository.save(requestMapper.toEntity(dto)));
    }

    @Override
    public UserResponseDto update(UUID id, UserRequestDto dto) {
        User user = repository.findById(id).orElseThrow(() -> {
            return notFound.get("error.user.notfound", id);
        });

        User newUser = requestMapper.toEntity(dto);
        if (newUser.getFirstName() != null)
            user.setFirstName(newUser.getFirstName());
        if (newUser.getLastName() != null)
            user.setLastName(newUser.getLastName());
        if (newUser.getPatronymic() != null)
            user.setPatronymic(newUser.getPatronymic());
        if (newUser.getRole() != null)
            user.setRole(newUser.getRole());
        if (newUser.getEmail() != null)
            user.setEmail(newUser.getEmail());
        if (newUser.getPasswordHash() != null)
            user.setPasswordHash(newUser.getPasswordHash());
        User updated = repository.save(newUser);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw notFound.get("error.user.notfound", id);
        }
    }
}
