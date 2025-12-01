package ua.unsober.backend.feature.admin;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ua.unsober.backend.common.BaseSystemTest;
import ua.unsober.backend.common.enums.Role;
import ua.unsober.backend.feature.user.User;
import ua.unsober.backend.feature.user.UserRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.unsober.backend.common.EntityAsserts.assertAdmin;

class AdminSystemTest extends BaseSystemTest {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AdminRequestMapper requestMapper;

    @Autowired
    AdminResponseMapper responseMapper;

    @AfterEach
    void tearDown() {
        adminRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @Test
    void createAsAdminShouldReturnCreatedAdmin() throws Exception {
        User user = User.builder()
                .firstName("Name")
                .lastName("Last name")
                .patronymic("Patronymic")
                .role(Role.ADMIN)
                .email("a@example.com")
                .passwordHash("a".repeat(60))
                .build();
        Admin admin = Admin.builder()
                .user(user)
                .build();
        AdminRequestDto requestDto = requestMapper.toDto(admin);
        requestDto.setPassword("a".repeat(60));
        AdminResponseDto expected = responseMapper.toDto(admin);
        ResultActions result = mvc.perform(post("/admin")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isOk());
        assertAdmin(result, expected);
        assertEquals(1, adminRepository.count());
    }

    @Test
    void createAsStudentShouldBeForbidden() throws Exception {
        AdminRequestDto requestDto = requestMapper.toDto(admin());
        mvc.perform(post("/admin")
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isForbidden());
        assertEquals(0, adminRepository.count());
    }

    @Test
    void getByIdAsAdminShouldReturnAdmin() throws Exception {
        Admin saved = adminRepository.save(admin());
        AdminResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/admin/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
        ).andExpect(status().isOk());
        assertAdmin(result, expected);
    }

    @Test
    void getByIdAsStudentShouldReturnAdmin() throws Exception {
        Admin saved = adminRepository.save(admin());
        AdminResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/admin/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
        ).andExpect(status().isOk());
        assertAdmin(result, expected);
    }

    @Test
    void getByIdNonExistentShouldReturnBadRequest() throws Exception {
        mvc.perform(get("/admin/{id}", UUID.randomUUID())
                .header("Authorization", "Bearer " + adminToken)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void getByEmailAsAdminShouldReturnAdmin() throws Exception {
        Admin saved = adminRepository.save(admin());
        AdminResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/admin/email/{email}", saved.getUser().getEmail())
                .header("Authorization", "Bearer " + adminToken)
        ).andExpect(status().isOk());
        assertAdmin(result, expected);
    }

    @Test
    void getByEmailAsStudentShouldReturnAdmin() throws Exception {
        Admin saved = adminRepository.save(admin());
        AdminResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/admin/email/{email}", saved.getUser().getEmail())
                .header("Authorization", "Bearer " + studentToken)
        ).andExpect(status().isOk());
        assertAdmin(result, expected);
    }

    @Test
    void updateAsAdminShouldUpdateAdmin() throws Exception {
        Admin saved = adminRepository.save(admin());
        saved.getUser().setFirstName("Updated");
        AdminRequestDto requestDto = requestMapper.toDto(saved);
        AdminResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(patch("/admin/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isOk());
        assertAdmin(result, expected);
    }

    @Test
    void updateAsStudentShouldBeForbidden() throws Exception {
        Admin saved = adminRepository.save(admin());
        AdminRequestDto requestDto = requestMapper.toDto(saved);
        mvc.perform(patch("/admin/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isForbidden());
    }

    @Test
    void deleteAsAdminShouldDeleteAdmin() throws Exception {
        Admin saved = adminRepository.save(admin());
        mvc.perform(delete("/admin/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
        ).andExpect(status().isOk());
        assertEquals(0, adminRepository.count());
    }

    @Test
    void deleteAsStudentShouldBeForbidden() throws Exception {
        Admin saved = adminRepository.save(admin());
        mvc.perform(delete("/admin/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
        ).andExpect(status().isForbidden());
        assertEquals(1, adminRepository.count());
    }

    private Admin admin() {
        User user = User.builder()
                .firstName("Name")
                .lastName("Last name")
                .patronymic("Patronymic")
                .role(Role.ADMIN)
                .email("b@example.com")
                .passwordHash("a".repeat(60))
                .build();
        userRepository.save(user);
        return Admin.builder()
                .user(user)
                .build();
    }

}
