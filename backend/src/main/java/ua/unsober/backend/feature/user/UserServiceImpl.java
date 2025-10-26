package ua.unsober.backend.feature.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
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

    private static final Marker USER_ACTION = MarkerFactory.getMarker("USER_ACTION");

    @Override
    public UserResponseDto getById(UUID id) {
        log.info(USER_ACTION, "Fetching user with id={}...", id);
        return responseMapper.toDto(
                repository.findById(id).orElseThrow(() -> {
                    log.warn(USER_ACTION, "Attempt to fetch a non-existent user with id={}", id);
                    return notFound.get("error.user.notfound", id);
                }));
    }

    @Override
    public UserResponseDto getByEmail(String email) {
        log.info(USER_ACTION, "Fetching user with email={}...", email);
        return responseMapper.toDto(
                repository.findByEmail(email).orElseThrow(() -> {
                    log.warn(USER_ACTION, "Attempt to fetch a non-existent user with email={}", email);
                    return notFound.get("error.user.notfound", email);
                }));
    }

    @Override
    public UserResponseDto create(UserRequestDto dto) {
        log.info(USER_ACTION, "Creating new user...");
        return responseMapper.toDto(
                repository.save(requestMapper.toEntity(dto)));
    }

    @Override
    public UserResponseDto update(UUID id, UserRequestDto dto) {
        User user = repository.findById(id).orElseThrow(() -> {
            log.warn(USER_ACTION, "Attempt to update non-existing user with id={}", id);
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
        log.info(USER_ACTION, "Deleting user with id={}...", id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            log.warn(USER_ACTION, "Attempt to delete non-existing user with id={}", id);
            throw notFound.get("error.user.notfound", id);
        }
    }
}
