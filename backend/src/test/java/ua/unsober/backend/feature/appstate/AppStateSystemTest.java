package ua.unsober.backend.feature.appstate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import ua.unsober.backend.common.BaseSystemTest;
import ua.unsober.backend.common.enums.EnrollmentStage;
import ua.unsober.backend.common.enums.Term;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AppStateSystemTest extends BaseSystemTest {

    @Autowired
    private AppStateRepository appStateRepository;

    @AfterEach
    void tearDown() {
        appStateRepository.deleteAll();
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getAppStateShouldReturnCurrentState(String token) throws Exception {
        AppState saved = appState();
        ResultActions result = mvc.perform(get("/app-state")
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        result.andExpect(mvcResult -> {
            String content = mvcResult.getResponse().getContentAsString();
            assertTrue(content.contains(saved.getCurrentYear().toString()));
            assertTrue(content.contains(saved.getTerm().name()));
            assertTrue(content.contains(saved.getEnrollmentStage().name()));
        });
    }

    @Test
    void setCurrentYearAsAdminShouldUpdateYear() throws Exception {
        AppState saved = appState();
        mvc.perform(patch("/app-state/year")
                .header("Authorization", "Bearer " + adminToken)
                .param("year", "2026")
        ).andExpect(status().isOk());
        AppState updated = appStateRepository.findById(saved.getId()).orElseThrow();
        assertEquals(2026, updated.getCurrentYear());
    }

    @Test
    void setCurrentYearAsStudentShouldBeForbidden() throws Exception {
        AppState saved = appState();
        mvc.perform(patch("/app-state/year")
                .header("Authorization", "Bearer " + studentToken)
                .param("year", "2026")
        ).andExpect(status().isForbidden());
        AppState unchanged = appStateRepository.findById(saved.getId()).orElseThrow();
        assertEquals(2025, unchanged.getCurrentYear());
    }

    @Test
    void setCurrentTermAsAdminShouldUpdateTerm() throws Exception {
        AppState saved = appState();
        mvc.perform(patch("/app-state/term")
                .header("Authorization", "Bearer " + adminToken)
                .param("term", "SPRING")
        ).andExpect(status().isOk());
        AppState updated = appStateRepository.findById(saved.getId()).orElseThrow();
        assertEquals(Term.SPRING, updated.getTerm());
    }

    @Test
    void setCurrentTermAsStudentShouldBeForbidden() throws Exception {
        AppState saved = appState();
        mvc.perform(patch("/app-state/term")
                .header("Authorization", "Bearer " + studentToken)
                .param("term", "SPRING")
        ).andExpect(status().isForbidden());
        AppState unchanged = appStateRepository.findById(saved.getId()).orElseThrow();
        assertEquals(Term.AUTUMN, unchanged.getTerm());
    }

    @Test
    void setCurrentStageAsAdminShouldUpdateStage() throws Exception {
        AppState saved = appState();
        mvc.perform(patch("/app-state/stage")
                .header("Authorization", "Bearer " + adminToken)
                .param("stage", "CORRECTION")
        ).andExpect(status().isOk());
        AppState updated = appStateRepository.findById(saved.getId()).orElseThrow();
        assertEquals(EnrollmentStage.CORRECTION, updated.getEnrollmentStage());
    }

    @Test
    void setCurrentStageAsStudentShouldBeForbidden() throws Exception {
        AppState saved = appState();
        mvc.perform(patch("/app-state/stage")
                .header("Authorization", "Bearer " + studentToken)
                .param("stage", "CORRECTION")
        ).andExpect(status().isForbidden());
        AppState unchanged = appStateRepository.findById(saved.getId()).orElseThrow();
        assertEquals(EnrollmentStage.COURSES, unchanged.getEnrollmentStage());
    }

    private AppState appState() {
        return appStateRepository.save(AppState.builder()
                .id(1)
                .currentYear(2025)
                .term(Term.AUTUMN)
                .enrollmentStage(EnrollmentStage.COURSES)
                .build());
    }

}
