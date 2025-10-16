package ua.unsober.backend.feature.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final AdminRequestMapper requestMapper;
    private final AdminResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    private static final Marker ADMIN_ACTION = MarkerFactory.getMarker("ADMIN_ACTION");
    private static final Marker ADMIN_ERROR = MarkerFactory.getMarker("ADMIN_ERROR");

    @Override
    public AdminResponseDto create(AdminRequestDto dto) {
        log.info(ADMIN_ACTION, "Creating new admin...");
        Admin saved = adminRepository.save(requestMapper.toEntity(dto));
        log.info(ADMIN_ACTION, "Admin created with id={}", saved.getId());
        return responseMapper.toDto(saved);
    }

    @Override
    public AdminResponseDto getById(UUID id) {
        log.info(ADMIN_ACTION, "Fetching admin with id={}...", id);
        Admin admin = adminRepository.findById(id).orElseThrow(() -> {
            log.warn(ADMIN_ERROR, "Attempt to fetch non-existing admin with id={}", id);
            return notFound.get("error.admin.notfound", id);
        });
        return responseMapper.toDto(admin);
    }

    @Override
    public AdminResponseDto update(UUID id, AdminRequestDto dto) {
        log.info(ADMIN_ACTION, "Updating admin with id={}...", id);
        Admin admin = adminRepository.findById(id).orElseThrow(() -> {
            log.warn(ADMIN_ERROR, "Attempt to update non-existing admin with id={}", id);
            return notFound.get("error.admin.notfound", id);
        });

        Admin newAdmin = requestMapper.toEntity(dto);
        if (newAdmin.getEmail() != null) admin.setEmail(newAdmin.getEmail());
        if (newAdmin.getPasswordHash() != null) admin.setPasswordHash(newAdmin.getPasswordHash());

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
}

