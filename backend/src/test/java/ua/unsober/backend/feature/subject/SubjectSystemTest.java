package ua.unsober.backend.feature.subject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ua.unsober.backend.common.BaseSystemTest;
import ua.unsober.backend.common.enums.EducationLevel;
import ua.unsober.backend.common.enums.Term;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.unsober.backend.common.EntityAsserts.assertSubject;
import static ua.unsober.backend.common.EntityAsserts.assertSubjectArray;

class SubjectSystemTest extends BaseSystemTest {

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    SubjectRequestMapper requestMapper;

    @Autowired
    SubjectResponseMapper responseMapper;

    @AfterEach
    void tearDown() {
        subjectRepository.deleteAll();
    }

    @Test
    void createAsAdminShouldReturnCreatedSubject() throws Exception {
        Subject subject = subjectEntity();
        SubjectRequestDto requestDto = requestMapper.toDto(subject);
        SubjectResponseDto responseDto = responseMapper.toDto(subject);
        ResultActions result = mvc.perform(post("/subject")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isOk());
        assertSubject(result, responseDto);
        assertEquals(1, subjectRepository.count());
    }

    @Test
    void createAsStudentShouldBeForbidden() throws Exception {
        SubjectRequestDto requestDto = requestMapper.toDto(subjectEntity());
        mvc.perform(post("/subject")
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isForbidden());
        assertEquals(0, subjectRepository.count());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getAllShouldReturnAllSubjects(String token) throws Exception {
        Subject saved = subjectRepository.save(subjectEntity());
        SubjectResponseDto expectedDto = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/subject")
                .header("Authorization", "Bearer " + token)
                .param("page", "0")
                .param("size", "10")
        ).andExpect(status().isOk());
        assertSubjectArray(result, 0, expectedDto);
        assertEquals(1, subjectRepository.count());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdShouldReturnSubject(String token) throws Exception {
        Subject saved = subjectRepository.save(subjectEntity());
        SubjectResponseDto expectedDto = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/subject/{id}", saved.getId())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertSubject(result, expectedDto);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdNonExistentShouldReturnBadRequest(String token) throws Exception {
        mvc.perform(get("/subject/{id}", UUID.randomUUID())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void updateAsAdminShouldUpdateSubject() throws Exception {
        Subject saved = subjectRepository.save(subjectEntity());
        saved.setName("Updated");
        SubjectRequestDto updatedDto = requestMapper.toDto(saved);
        SubjectResponseDto expectedDto = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(patch("/subject/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedDto))
        ).andExpect(status().isOk());
        assertSubject(result, expectedDto);
    }

    @Test
    void updateAsStudentShouldBeForbidden() throws Exception {
        Subject saved = subjectRepository.save(subjectEntity());
        SubjectRequestDto updatedDto = requestMapper.toDto(subjectEntity());
        mvc.perform(patch("/subjects/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedDto))
        ).andExpect(status().isForbidden());
    }

    @Test
    void deleteAsAdminShouldRemoveSubject() throws Exception {
        Subject saved = subjectRepository.save(subjectEntity());
        mvc.perform(delete("/subject/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
        ).andExpect(status().isOk());
        assertEquals(0, subjectRepository.count());
    }

    @Test
    void deleteAsStudentShouldFail() throws Exception {
        Subject saved = subjectRepository.save(subjectEntity());
        mvc.perform(delete("/subject/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
        ).andExpect(status().isForbidden());
        assertEquals(1, subjectRepository.count());
    }

    private Subject subjectEntity() {
        return Subject.builder()
                .name("subject")
                .annotation("annotation")
                .facultyName("faculty")
                .departmentName("department")
                .educationLevel(EducationLevel.BATCHELOR)
                .credits(BigDecimal.valueOf(3))
                .hoursPerWeek(4)
                .term(Term.AUTUMN)
                .build();
    }

}
