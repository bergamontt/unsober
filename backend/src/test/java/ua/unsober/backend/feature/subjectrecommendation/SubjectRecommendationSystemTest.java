package ua.unsober.backend.feature.subjectrecommendation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ua.unsober.backend.common.BaseSystemTest;
import ua.unsober.backend.common.enums.EducationLevel;
import ua.unsober.backend.common.enums.Recommendation;
import ua.unsober.backend.common.enums.Term;
import ua.unsober.backend.feature.speciality.Speciality;
import ua.unsober.backend.feature.speciality.SpecialityRepository;
import ua.unsober.backend.feature.subject.Subject;
import ua.unsober.backend.feature.subject.SubjectRepository;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.unsober.backend.common.EntityAsserts.assertSubjectRecommendation;
import static ua.unsober.backend.common.EntityAsserts.assertSubjectRecommendationArray;

class SubjectRecommendationSystemTest extends BaseSystemTest {

    @Autowired
    SubjectRecommendationRepository recommendationRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    SpecialityRepository specialityRepository;

    @Autowired
    SubjectRecommendationRequestMapper requestMapper;

    @Autowired
    SubjectRecommendationResponseMapper responseMapper;

    @AfterEach
    void tearDown() {
        recommendationRepository.deleteAll();
        subjectRepository.deleteAll();
        specialityRepository.deleteAll();
    }

    @Test
    void createAsAdminShouldReturnNewRecommendation() throws Exception {
        SubjectRecommendation rec = subjectRecommendation();
        SubjectRecommendationRequestDto requestDto = requestMapper.toDto(rec);
        SubjectRecommendationResponseDto expected = responseMapper.toDto(rec);
        ResultActions result = mvc.perform(post("/subject-recommendation")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isOk());
        assertSubjectRecommendation(result, expected);
        assertEquals(1, recommendationRepository.count());
    }

    @Test
    void createAsStudentShouldBeForbidden() throws Exception {
        SubjectRecommendationRequestDto requestDto = requestMapper.toDto(subjectRecommendation());
        mvc.perform(post("/subject-recommendation")
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isForbidden());
        assertEquals(0, recommendationRepository.count());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getAllShouldReturnAllRecommendations(String token) throws Exception {
        SubjectRecommendation saved = recommendationRepository.save(subjectRecommendation());
        SubjectRecommendationResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/subject-recommendation")
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertSubjectRecommendationArray(result, 0, expected);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdShouldReturnRecommendation(String token) throws Exception {
        SubjectRecommendation saved = recommendationRepository.save(subjectRecommendation());
        SubjectRecommendationResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/subject-recommendation/{id}", saved.getId())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertSubjectRecommendation(result, expected);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdNonExistentShouldReturnBadRequest(String token) throws Exception {
        mvc.perform(get("/subject-recommendation/{id}", UUID.randomUUID())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getBySubjectAndSpecialityShouldReturnRecommendation(String token) throws Exception {
        SubjectRecommendation saved = recommendationRepository.save(subjectRecommendation());
        SubjectRecommendationResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/subject-recommendation/subject-and-speciality")
                .header("Authorization", "Bearer " + token)
                .param("subjectId", saved.getSubject().getId().toString())
                .param("specialityId", saved.getSpeciality().getId().toString())
        ).andExpect(status().isOk());
        assertSubjectRecommendation(result, expected);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void existsBySubjectAndSpecialityShouldReturnTrue(String token) throws Exception {
        SubjectRecommendation saved = recommendationRepository.save(subjectRecommendation());
        mvc.perform(get("/subject-recommendation/exists/subject-and-speciality")
                        .header("Authorization", "Bearer " + token)
                        .param("subjectId", saved.getSubject().getId().toString())
                        .param("specialityId", saved.getSpeciality().getId().toString())
                ).andExpect(status().isOk())
                .andExpect(result -> assertEquals("true", result.getResponse().getContentAsString()));
    }

    @Test
    void updateAsAdminShouldUpdateRecommendation() throws Exception {
        SubjectRecommendation saved = recommendationRepository.save(subjectRecommendation());
        saved.setRecommendation(Recommendation.PROF_ORIENTED);
        SubjectRecommendationRequestDto updated = requestMapper.toDto(saved);
        SubjectRecommendationResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(patch("/subject-recommendation/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updated))
        ).andExpect(status().isOk());
        assertSubjectRecommendation(result, expected);
    }

    @Test
    void updateAsStudentShouldBeForbidden() throws Exception {
        SubjectRecommendation saved = recommendationRepository.save(subjectRecommendation());
        SubjectRecommendationRequestDto updated = requestMapper.toDto(saved);
        mvc.perform(patch("/subject-recommendation/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updated))
        ).andExpect(status().isForbidden());
    }

    private SubjectRecommendation subjectRecommendation() {
        Subject subject = subjectRepository.save(Subject.builder()
                .name("Math")
                .hoursPerWeek(4)
                .term(Term.AUTUMN)
                .educationLevel(EducationLevel.BATCHELOR)
                .credits(BigDecimal.valueOf(5))
                .build());
        Speciality speciality = specialityRepository.save(Speciality.builder()
                .name("Software Engineering")
                .build());
        return SubjectRecommendation.builder()
                .subject(subject)
                .speciality(speciality)
                .recommendation(Recommendation.MANDATORY)
                .build();
    }

}