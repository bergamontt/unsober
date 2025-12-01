package ua.unsober.backend.feature.auth;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;

import ua.unsober.backend.common.BaseSystemTest;
import ua.unsober.backend.common.enums.Role;
import ua.unsober.backend.feature.user.User;
import ua.unsober.backend.feature.user.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.unsober.backend.common.EntityAsserts.assertAuthResponse;

class AuthSystemTest extends BaseSystemTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_PASSWORD = "password123";

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void signInAsStudentWithValidCredentialsShouldReturnToken() throws Exception {
        createUser(Role.STUDENT);
        AuthRequest authRequest = AuthRequest.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();
        ResultActions result = mvc.perform(post("/auth/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(authRequest))
        ).andExpect(status().isOk());
        assertAuthResponse(result);
    }

    @Test
    void signInAsAdminWithValidCredentialsShouldReturnToken() throws Exception {
        createUser(Role.ADMIN);
        AuthRequest authRequest = AuthRequest.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();
        ResultActions result = mvc.perform(post("/auth/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(authRequest))
        ).andExpect(status().isOk());
        assertAuthResponse(result);
    }

    @Test
    void signInWithInvalidPasswordShouldReturnUnauthorized() throws Exception {
        AuthRequest authRequest = AuthRequest.builder()
                .email(TEST_EMAIL)
                .password("wrong")
                .build();
        mvc.perform(post("/auth/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(authRequest))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    void signInWithNonExistentEmailShouldReturnUnauthorized() throws Exception {
        AuthRequest authRequest = AuthRequest.builder()
                .email("nonexistent@example.com")
                .password(TEST_PASSWORD)
                .build();
        mvc.perform(post("/auth/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(authRequest))
        ).andExpect(status().isUnauthorized());
    }

    private void createUser(Role role) {
        User user = User.builder()
                .firstName("Name")
                .lastName("Last name")
                .patronymic("Patronymic")
                .role(role)
                .email(TEST_EMAIL)
                .passwordHash(passwordEncoder.encode(TEST_PASSWORD))
                .build();
        userRepository.save(user);
    }

}
