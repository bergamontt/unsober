package ua.unsober.backend.feature.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.enums.Role;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;
import ua.unsober.backend.feature.user.User;
import ua.unsober.backend.feature.user.UserRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final AdminRequestMapper requestMapper;
    private final AdminResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    private static final Marker ADMIN_ACTION = MarkerFactory.getMarker("ADMIN_ACTION");
    private static final Marker ADMIN_ERROR = MarkerFactory.getMarker("ADMIN_ERROR");

    @Override
    public AdminResponseDto create(AdminRequestDto dto) {
        log.info(ADMIN_ACTION, "Creating new admin...");
        Admin admin = requestMapper.toEntity(dto);
        User savedUser = userRepository.save(admin.getUser());
        admin.setUser(savedUser);
        Admin saved = adminRepository.save(admin);
        log.info(ADMIN_ACTION, "Admin created with id={}", saved.getId());
        return responseMapper.toDto(saved);
    }

    @Override
    public List<AdminResponseDto> getAll() {
        log.info(ADMIN_ACTION, "Fetching all admins...");
        return adminRepository.findAll()
                .stream()
                .map(responseMapper::toDto)
                .toList();
    }

    @Override
    public AdminResponseDto getById(UUID id) {
        log.info(ADMIN_ACTION, "Fetching admin with id={}...", id);
        return responseMapper.toDto(
                adminRepository.findById(id).orElseThrow(() -> {
                    log.warn(ADMIN_ERROR, "Attempt to fetch non-existing admin with id={}", id);
                    return notFound.get("error.admin.notfound", id);
                }));
    }

    @Override
    public AdminResponseDto update(UUID id, AdminRequestDto dto) {
        log.info(ADMIN_ACTION, "Updating admin with id={}...", id);
        Admin admin = adminRepository.findById(id).orElseThrow(() -> {
            log.warn(ADMIN_ERROR, "Attempt to update non-existing admin with id={}", id);
            return notFound.get("error.admin.notfound", id);
        });

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
        log.info(ADMIN_ACTION, "Successfully updated admin with id={}", id);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        log.info(ADMIN_ACTION, "Deleting admin with id={}...", id);
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            log.info(ADMIN_ACTION, "Admin with id={} deleted", id);
        } else {
            log.warn(ADMIN_ERROR, "Attempt to delete non-existing admin with id={}", id);
            throw notFound.get("error.admin.notfound", id);
        }
    }

    @Override
    public AdminResponseDto getByEmail(String email) {
        log.info(ADMIN_ACTION, "Fetching admin by email...");
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                notFound.get("error.user.notfound", email)
        );
        if (user.getRole() == Role.ADMIN) {
            return responseMapper.toDto(adminRepository.findByUserId(user.getId())
                    .orElseThrow(() -> {
                        log.warn(ADMIN_ACTION, "Attempt to fetch an admin by non-existing email");
                        return notFound.get("error.admin.notfound", email);
                    }));
        } else {
            throw notFound.get("error.admin.notfound", email);
        }
    }
}
