package ua.unsober.backend.feature.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.unsober.backend.feature.student.Student;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class AuthUserDetails implements UserDetails {
    private final Student student;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return student.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return student.getEmail();
    }
}