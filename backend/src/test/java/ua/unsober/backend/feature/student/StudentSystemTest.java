package ua.unsober.backend.feature.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ua.unsober.backend.common.BaseSystemTest;
import ua.unsober.backend.common.enums.Role;
import ua.unsober.backend.common.enums.StudentStatus;
import ua.unsober.backend.feature.speciality.Speciality;
import ua.unsober.backend.feature.speciality.SpecialityRepository;
import ua.unsober.backend.feature.user.User;
import ua.unsober.backend.feature.user.UserRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.unsober.backend.common.EntityAsserts.assertStudent;
import static ua.unsober.backend.common.EntityAsserts.assertStudentArray;

class StudentSystemTest extends BaseSystemTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private StudentRequestMapper requestMapper;

    @Autowired
    private StudentResponseMapper responseMapper;

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
        userRepository.deleteAll();
        specialityRepository.deleteAll();
    }

    @Test
    void createAsAdminShouldReturnNewStudent() throws Exception {
        Student st = student();
        st.getUser().setEmail("a@exmaple.com");
        StudentRequestDto requestDto = requestMapper.toDto(st);
        StudentResponseDto expectedDto = responseMapper.toDto(st);
        ResultActions result = mvc.perform(post("/student")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isOk());
        assertStudent(result, expectedDto);
        assertEquals(1, studentRepository.count());
    }

    @Test
    void createAsStudentShouldReturnForbidden() throws Exception {
        Student st = student();
        StudentRequestDto requestDto = requestMapper.toDto(st);
        mvc.perform(post("/student")
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isForbidden());
        assertEquals(0, studentRepository.count());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getAllShouldReturnAllStudents(String token) throws Exception {
        Student saved = studentRepository.save(student());
        StudentResponseDto expectedDto = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/student")
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertStudentArray(result, 0, expectedDto);
        assertEquals(1, studentRepository.count());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdShouldReturnStudent(String token) throws Exception {
        Student saved = studentRepository.save(student());
        StudentResponseDto expectedDto = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/student/uuid/{id}", saved.getId())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertStudent(result, expectedDto);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdNonExistentShouldReturnBadRequest(String token) throws Exception {
        mvc.perform(get("/student/uuid/{id}", UUID.randomUUID())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByEmailShouldReturnStudent(String token) throws Exception {
        Student saved = studentRepository.save(student());
        StudentResponseDto expectedDto = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/student/email/{email}", saved.getUser().getEmail())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertStudent(result, expectedDto);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByEmailNonExistentShouldReturnBadRequest(String token) throws Exception {
        mvc.perform(get("/student/email/{email}", "e@example.com")
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void updateAsAdminShouldModifyStudent() throws Exception {
        Student saved = studentRepository.save(student());
        saved.setStatus(StudentStatus.EXPELLED);
        StudentRequestDto requestDto = requestMapper.toDto(saved);
        StudentResponseDto expectedDto = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(patch("/student/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isOk());
        assertStudent(result, expectedDto);
    }

    @Test
    void updateAsStudentShouldReturnForbidden() throws Exception {
        Student saved = studentRepository.save(student());
        StudentRequestDto requestDto = requestMapper.toDto(saved);
        mvc.perform(patch("/student/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isForbidden());
    }

    @Test
    void deleteAsAdminShouldRemoveStudent() throws Exception {
        Student saved = studentRepository.save(student());
        mvc.perform(delete("/student/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
        ).andExpect(status().isOk());
        assertEquals(0, studentRepository.count());
    }

    @Test
    void deleteAsStudentShouldReturnForbidden() throws Exception {
        Student saved = studentRepository.save(student());
        mvc.perform(delete("/student/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
        ).andExpect(status().isForbidden());
        assertEquals(1, studentRepository.count());
    }

    private Speciality speciality() {
        Speciality s = Speciality.builder()
                .name("Speciality")
                .description("Description")
                .build();
        return specialityRepository.save(s);
    }

    private User user() {
        User u = User.builder()
                .firstName("Name")
                .lastName("Last name")
                .patronymic("Patronymic")
                .role(Role.STUDENT)
                .email("e@example.com")
                .passwordHash("a".repeat(60))
                .build();
        return userRepository.save(u);
    }

    private Student student() {
        return Student.builder()
                .user(user())
                .speciality(speciality())
                .recordBookNumber("RB123")
                .studyYear(2)
                .status(StudentStatus.STUDYING)
                .build();
    }

}
