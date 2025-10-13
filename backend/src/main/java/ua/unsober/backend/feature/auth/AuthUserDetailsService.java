package ua.unsober.backend.feature.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.unsober.backend.feature.student.StudentRepository;

@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {
    private final StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return studentRepository.findByEmail(email)
                .map(AuthUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }
}