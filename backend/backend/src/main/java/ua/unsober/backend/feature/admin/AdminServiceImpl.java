package ua.unsober.backend.feature.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AdminRequestMapper requestMapper;
    private final AdminResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    @Override
    public AdminResponseDto create(AdminRequestDto dto) {
        return responseMapper.toDto(
                adminRepository.save(requestMapper.toEntity(dto))
        );
    }

    @Override
    public AdminResponseDto getById(UUID id) {
        return responseMapper.toDto(
                adminRepository.findById(id).orElseThrow(()->
                        notFound.get("error.admin.notfound", id))
        );
    }

    @Override
    public AdminResponseDto update(UUID id, AdminRequestDto dto) {
        Admin admin = adminRepository.findById(id).orElseThrow(() ->
                notFound.get("error.admin.notfound", id));
        Admin newAdmin = requestMapper.toEntity(dto);
        if(newAdmin.getEmail() != null)
            admin.setEmail(newAdmin.getEmail());
        if(newAdmin.getPasswordHash() != null)
            admin.setPasswordHash(newAdmin.getPasswordHash());
        Admin updated = adminRepository.save(admin);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        if(adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
        } else {
            throw notFound.get("error.admin.notfound", id);
        }
    }
}
