package ua.unsober.backend.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.unsober.backend.common.enums.Role;
import ua.unsober.backend.feature.auth.JwtTokenService;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class BaseSystemTest {
    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper mapper;

    @Autowired
    protected JwtTokenService jwtService;

    @MockitoBean
    protected JavaMailSender javaMailSender;

    protected String studentToken;
    protected String adminToken;

    @BeforeEach
    void generateTokens() {
        studentToken = jwtService.generateToken(
                new UsernamePasswordAuthenticationToken(
                        "student", null, List.of(new SimpleGrantedAuthority(Role.STUDENT.name()))
                )
        );
        adminToken = jwtService.generateToken(
                new UsernamePasswordAuthenticationToken(
                        "admin", null, List.of(new SimpleGrantedAuthority(Role.ADMIN.name()))
                )
        );
    }
}
