package ua.unsober.backend.feature.teacher;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ua.unsober.backend.common.BaseSystemTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.unsober.backend.common.EntityAsserts.assertTeacher;
import static ua.unsober.backend.common.EntityAsserts.assertTeacherArray;

class TeacherSystemTest extends BaseSystemTest {

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    TeacherRequestMapper requestMapper;

    @Autowired
    TeacherResponseMapper responseMapper;

    @AfterEach
    void tearDown() {
        teacherRepository.deleteAll();
    }

    @Test
    void createAsAdminShouldReturnCreatedTeacher() throws Exception {
        Teacher teacher = teacher();
        TeacherRequestDto requestDto = requestMapper.toDto(teacher);
        TeacherResponseDto responseDto = responseMapper.toDto(teacher);
        ResultActions result = mvc.perform(post("/teacher")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isOk());
        assertTeacher(result, responseDto);
        assertEquals(1, teacherRepository.count());
    }

    @Test
    void createAsStudentShouldReturnForbidden() throws Exception {
        TeacherRequestDto requestDto = requestMapper.toDto(teacher());
        mvc.perform(post("/teacher")
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isForbidden());
        assertEquals(0, teacherRepository.count());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getAllShouldReturnAllTeachers(String token) throws Exception {
        Teacher saved = teacherRepository.save(teacher());
        TeacherResponseDto expectedDto = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/teacher")
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertTeacherArray(result, 0, expectedDto);
        assertEquals(1, teacherRepository.count());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdShouldReturnTeacher(String token) throws Exception {
        Teacher saved = teacherRepository.save(teacher());
        TeacherResponseDto expectedDto = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/teacher/{id}", saved.getId())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertTeacher(result, expectedDto);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdNonExistentShouldReturnBadRequest(String token) throws Exception {
        mvc.perform(get("/teacher/{id}", UUID.randomUUID())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void updateAsAdminShouldModifyTeacher() throws Exception {
        Teacher saved = teacherRepository.save(teacher());
        saved.setFirstName("Updated");
        TeacherRequestDto requestDto = requestMapper.toDto(saved);
        TeacherResponseDto responseDto = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(patch("/teacher/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isOk());
        assertTeacher(result, responseDto);
    }

    @Test
    void updateAsStudentShouldBeForbidden() throws Exception {
        Teacher saved = teacherRepository.save(teacher());
        TeacherRequestDto requestDto = requestMapper.toDto(saved);
        mvc.perform(patch("/subjects/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isForbidden());
    }

    @Test
    void deleteAsAdminShouldRemoveTeacher() throws Exception {
        Teacher saved = teacherRepository.save(teacher());
        mvc.perform(delete("/teacher/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
        ).andExpect(status().isOk());
        assertEquals(0, teacherRepository.count());
    }

    @Test
    void deleteAsStudentShouldReturnForbidden() throws Exception {
        Teacher saved = teacherRepository.save(teacher());
        mvc.perform(delete("/teacher/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
        ).andExpect(status().isForbidden());
    }

    private Teacher teacher() {
        return Teacher.builder()
                .firstName("Name")
                .lastName("Last name")
                .patronymic("patronymic")
                .email("email@example.com")
                .build();
    }

}
