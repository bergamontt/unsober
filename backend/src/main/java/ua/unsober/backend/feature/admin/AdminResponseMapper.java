package ua.unsober.backend.feature.admin;

import org.springframework.stereotype.Component;
import ua.unsober.backend.feature.user.User;

@Component
public class AdminResponseMapper {
    public AdminResponseDto toDto(Admin admin) {
        if (admin == null)
            return null;
        AdminResponseDto response = AdminResponseDto.builder()
                .id(admin.getId())
                .build();
        if (admin.getUser() != null) {
            User user = admin.getUser();
            response.setFirstName(user.getFirstName());
            response.setLastName(user.getLastName());
            response.setPatronymic(user.getPatronymic());
            response.setEmail(user.getEmail());
        }
        return response;
    }
}
