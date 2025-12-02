package ua.unsober.backend.feature.department;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ua.unsober.backend.common.BaseSystemTest;
import ua.unsober.backend.feature.faculty.Faculty;
import ua.unsober.backend.feature.faculty.FacultyRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.unsober.backend.common.EntityAsserts.assertDepartment;
import static ua.unsober.backend.common.EntityAsserts.assertDepartmentArray;

class DepartmentSystemTest extends BaseSystemTest {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    DepartmentRequestMapper requestMapper;

    @Autowired
    DepartmentResponseMapper responseMapper;

    @Autowired
    FacultyRepository facultyRepository;

    @AfterEach
    void tearDown() {
        departmentRepository.deleteAll();
        facultyRepository.deleteAll();
    }

    @Test
    void createAsAdminShouldReturnNewDepartment() throws Exception {
        Department department = department();
        DepartmentRequestDto requestDto = requestMapper.toDto(department);
        DepartmentResponseDto responseDto = responseMapper.toDto(department);
        ResultActions result = mvc.perform(post("/department")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());
        assertDepartment(result, responseDto);
        assertEquals(1, departmentRepository.count());
    }

    @Test
    void createAsStudentShouldReturnForbidden() throws Exception {
        DepartmentRequestDto requestDto = requestMapper.toDto(department());
        mvc.perform(post("/department")
                        .header("Authorization", "Bearer " + studentToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(status().isForbidden());
        assertEquals(0, departmentRepository.count());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getAllShouldReturnAllDepartments(String token) throws Exception {
        Department saved = departmentRepository.save(department());
        DepartmentResponseDto expectedDto = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/department")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
        assertDepartmentArray(result, 0, expectedDto);
        assertEquals(1, departmentRepository.count());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdAsAdminShouldReturnDepartmentWithGivenId(String token) throws Exception {
        Department saved = departmentRepository.save(department());
        DepartmentResponseDto expectedDto = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/department/{id}", saved.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
        assertDepartment(result, expectedDto);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdNonExistentShouldReturnBadRequest(String token) throws Exception {
        mvc.perform(get("/department/{id}", UUID.randomUUID())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateAsAdminShouldModifyDepartment() throws Exception {
        Department saved = departmentRepository.save(department());
        saved.setName("Updated");
        DepartmentRequestDto requestDto = requestMapper.toDto(saved);
        DepartmentResponseDto responseDto = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(patch("/department/{id}", saved.getId())
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());
        assertDepartment(result, responseDto);
    }

    @Test
    void updateAsStudentShouldBeForbidden() throws Exception {
        Department saved = departmentRepository.save(department());
        DepartmentRequestDto requestDto = requestMapper.toDto(saved);
        mvc.perform(patch("/department/{id}", saved.getId())
                        .header("Authorization", "Bearer " + studentToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteAsAdminShouldRemoveDepartment() throws Exception {
        Department saved = departmentRepository.save(department());
        mvc.perform(delete("/department/{id}", saved.getId())
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
        assertEquals(0, departmentRepository.count());
    }

    @Test
    void deleteAsStudentShouldReturnForbidden() throws Exception {
        Department saved = departmentRepository.save(department());
        mvc.perform(delete("/department/{id}", saved.getId())
                        .header("Authorization", "Bearer " + studentToken))
                .andExpect(status().isForbidden());
    }

    private Department department() {
        Faculty faculty = facultyRepository.save(Faculty.builder()
                .name("Name")
                .description("Description")
                .build());
        return Department.builder()
                .faculty(faculty)
                .name("Name")
                .description("Description")
                .build();
    }

}