package ua.unsober.backend.feature.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.unsober.backend.common.enums.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private String firstName;
    private String lastName;
    private String patronymic;
    private Role role;
    private String email;
    private String password;
}