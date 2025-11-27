package ua.unsober.backend.feature.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.enums.Role;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;
import ua.unsober.backend.feature.user.User;
import ua.unsober.backend.feature.user.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final AdminRequestMapper requestMapper;
    private final AdminResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    private static final String ADMIN_NOT_FOUND = "error.admin.notfound";
    private static final String USER_NOT_FOUND = "error.user.notfound";

    @Override
    public AdminResponseDto create(AdminRequestDto dto) {
        Admin admin = requestMapper.toEntity(dto);
        User savedUser = userRepository.save(admin.getUser());
        admin.setUser(savedUser);
        Admin saved = adminRepository.save(admin);
        return responseMapper.toDto(saved);
    }

    @Override
    public List<AdminResponseDto> getAll() {
        return adminRepository.findAll()
                .stream()
                .map(responseMapper::toDto)
                .toList();
    }

    @Override
    public AdminResponseDto getById(UUID id) {
        return responseMapper.toDto(
                adminRepository.findById(id).orElseThrow(() ->
                        notFound.get(ADMIN_NOT_FOUND, id)));
    }

    @Override
    public AdminResponseDto update(UUID id, AdminRequestDto dto) {
        Admin admin = adminRepository.findById(id).orElseThrow(() ->
                notFound.get(ADMIN_NOT_FOUND, id));

        Admin newAdmin = requestMapper.toEntity(dto);
        if (newAdmin.getUser() != null) {
            User newUser = newAdmin.getUser();
            if (newUser.getFirstName() != null)
                admin.getUser().setFirstName(newUser.getFirstName());
            if (newUser.getLastName() != null)
                admin.getUser().setLastName(newUser.getLastName());
            if (newUser.getPatronymic() != null)
                admin.getUser().setPatronymic(newUser.getPatronymic());
            if (newUser.getRole() != null)
                admin.getUser().setRole(newUser.getRole());
            if (newUser.getEmail() != null)
                admin.getUser().setEmail(newUser.getEmail());
            if (newUser.getPasswordHash() != null)
                admin.getUser().setPasswordHash(newUser.getPasswordHash());
        }

        User updatedUser = userRepository.save(admin.getUser());
        admin.setUser(updatedUser);
        Admin updated = adminRepository.save(admin);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
        } else {
            throw notFound.get(ADMIN_NOT_FOUND, id);
        }
    }

    @Override
    public AdminResponseDto getByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                notFound.get(USER_NOT_FOUND, email)
        );
        if (user.getRole() == Role.ADMIN) {
            return responseMapper.toDto(adminRepository.findByUserId(user.getId())
                    .orElseThrow(() -> notFound.get(ADMIN_NOT_FOUND, email)));
        } else {
            throw notFound.get(ADMIN_NOT_FOUND, email);
        }
    }
}
