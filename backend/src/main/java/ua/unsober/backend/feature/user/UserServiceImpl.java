package ua.unsober.backend.feature.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository repository;
    private final UserRequestMapper requestMapper;
    private final UserResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    private static final String USER_NOT_FOUND = "error.user.notfound";

    @Override
    public UserResponseDto getById(UUID id) {
        return responseMapper.toDto(
                repository.findById(id).orElseThrow(() ->
                        notFound.get(USER_NOT_FOUND, id)));
    }

    @Override
    public UserResponseDto getByEmail(String email) {
        return responseMapper.toDto(
                repository.findByEmail(email).orElseThrow(() ->
                        notFound.get(USER_NOT_FOUND, email)));
    }

    @Override
    public UserResponseDto create(UserRequestDto dto) {
        return responseMapper.toDto(
                repository.save(requestMapper.toEntity(dto)));
    }

    @Override
    public UserResponseDto update(UUID id, UserRequestDto dto) {
        User user = repository.findById(id).orElseThrow(() ->
                notFound.get(USER_NOT_FOUND, id));

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
        User updated = repository.save(user);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw notFound.get(USER_NOT_FOUND, id);
        }
    }
}
