package ua.unsober.backend.feature.terminfo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ua.unsober.backend.common.BaseSystemTest;
import ua.unsober.backend.common.enums.Term;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.unsober.backend.common.EntityAsserts.assertTermInfo;
import static ua.unsober.backend.common.EntityAsserts.assertTermInfoArray;

class TermInfoSystemTest extends BaseSystemTest {

    @Autowired
    TermInfoRepository termInfoRepository;

    @Autowired
    TermInfoRequestMapper requestMapper;

    @Autowired
    TermInfoResponseMapper responseMapper;

    @AfterEach
    void tearDown() {
        termInfoRepository.deleteAll();
    }

    @Test
    void createAsAdminShouldReturnCreatedTermInfo() throws Exception {
        TermInfo termInfo = termInfo();
        TermInfoRequestDto requestDto = requestMapper.toDto(termInfo);
        TermInfoResponseDto responseDto = responseMapper.toDto(termInfo);
        ResultActions result = mvc.perform(post("/term-info")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto))
                ).andExpect(status().isOk());
        assertTermInfo(result, responseDto);
        assertEquals(1, termInfoRepository.count());
    }

    @Test
    void createAsStudentShouldFail() throws Exception {
        TermInfoRequestDto requestDto = requestMapper.toDto(termInfo());
        mvc.perform(post("/term-info")
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isForbidden());
        assertEquals(0, termInfoRepository.count());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getAllShouldReturnAllTermInfo(String token) throws Exception {
        TermInfo saved = termInfoRepository.save(termInfo());
        TermInfoResponseDto expectedDto = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/term-info")
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertTermInfoArray(result, 0, expectedDto);
        assertEquals(1, termInfoRepository.count());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdShouldReturnTermInfo(String token) throws Exception {
        TermInfo saved = termInfoRepository.save(termInfo());
        TermInfoResponseDto expectedDto = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/term-info/uuid/{id}", saved.getId())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertTermInfo(result, expectedDto);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdNonExistentShouldReturnBadRequest(String token) throws Exception {
        mvc.perform(get("/term-info/uuid/{id}", UUID.randomUUID())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByYearAndTermShouldReturnTermInfo(String token) throws Exception {
        TermInfo saved = termInfoRepository.save(termInfo());
        TermInfoResponseDto expectedDto = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/term-info/year-and-term")
                .param("year", saved.getStudyYear().toString())
                .param("term", saved.getTerm().name())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertTermInfo(result, expectedDto);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByYearAndTermNonExistentShouldReturnBadRequest(String token) throws Exception {
        mvc.perform(get("/term-info/year-and-term")
                .param("year", "2099")
                .param("term", Term.SPRING.name())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void deleteAsAdminShouldRemoveTermInfo() throws Exception {
        TermInfo saved = termInfoRepository.save(termInfo());
        assertEquals(1, termInfoRepository.count());
        mvc.perform(delete("/term-info/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
        ).andExpect(status().isOk());
        assertEquals(0, termInfoRepository.count());
    }

    @Test
    void deleteAsStudentShouldFail() throws Exception {
        TermInfo saved = termInfoRepository.save(termInfo());
        assertEquals(1, termInfoRepository.count());
        mvc.perform(delete("/term-info/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
        ).andExpect(status().isForbidden());
        assertEquals(1, termInfoRepository.count());
    }

    private TermInfo termInfo() {
        return TermInfo.builder()
                .studyYear(2025)
                .term(Term.AUTUMN)
                .startDate(LocalDate.of(2025, 9, 1))
                .endDate(LocalDate.of(2025, 12, 20))
                .lenWeeks(16)
                .build();
    }

}