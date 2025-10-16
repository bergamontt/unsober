package ua.unsober.backend.feature.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.unsober.backend.feature.student.StudentRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {
    private final StudentRepository studentRepository;

    private static final Marker SECURITY = MarkerFactory.getMarker("SECURITY");

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info(SECURITY, "Loading user...");
        UserDetails user = studentRepository.findByEmail(email)
                .map(AuthUserDetails::new)
                .orElseThrow(() -> {
                    log.warn(SECURITY, "User was not found");
                    return new UsernameNotFoundException("User not found: " + email);
                });
        log.info("User loaded successfully");
        return user;
    }
}