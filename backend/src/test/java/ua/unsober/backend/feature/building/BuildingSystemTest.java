package ua.unsober.backend.feature.building;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ua.unsober.backend.common.BaseSystemTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.unsober.backend.common.EntityAsserts.assertBuilding;
import static ua.unsober.backend.common.EntityAsserts.assertBuildingArray;

class BuildingSystemTest extends BaseSystemTest {

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    BuildingRequestMapper requestMapper;

    @Autowired
    BuildingResponseMapper responseMapper;

    @AfterEach
    void clean() {
        buildingRepository.deleteAll();
    }

    @Test
    void createAsAdminShouldReturnCreatedBuilding() throws Exception {
        Building building = building();
        BuildingRequestDto requestDto = requestMapper.toDto(building);
        BuildingResponseDto expected = responseMapper.toDto(building);
        ResultActions result = mvc.perform(post("/building")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isOk());
        assertBuilding(result, expected);
        assertEquals(1, buildingRepository.count());
    }

    @Test
    void createAsStudentShouldBeForbidden() throws Exception {
        BuildingRequestDto requestDto = requestMapper.toDto(building());
        mvc.perform(post("/building")
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isForbidden());
        assertEquals(0, buildingRepository.count());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getAllShouldReturnAllBuildings(String token) throws Exception {
        Building saved = buildingRepository.save(building());
        BuildingResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/building")
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertBuildingArray(result, 0, expected);
        assertEquals(1, buildingRepository.count());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdShouldReturnBuilding(String token) throws Exception {
        Building saved = buildingRepository.save(building());
        BuildingResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/building/{id}", saved.getId())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertBuilding(result, expected);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdNonExistentShouldReturnBadRequest(String token) throws Exception {
        mvc.perform(get("/building/{id}", UUID.randomUUID())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void updateAsAdminShouldUpdateBuilding() throws Exception {
        Building saved = buildingRepository.save(building());
        saved.setName("Updated");
        BuildingRequestDto requestDto = requestMapper.toDto(saved);
        BuildingResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(patch("/building/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isOk());
        assertBuilding(result, expected);
    }

    @Test
    void updateAsStudentShouldBeForbidden() throws Exception {
        Building saved = buildingRepository.save(building());
        BuildingRequestDto requestDto = requestMapper.toDto(saved);
        mvc.perform(patch("/building/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isForbidden());
    }

    @Test
    void deleteAsAdminShouldDeleteBuilding() throws Exception {
        Building saved = buildingRepository.save(building());
        mvc.perform(delete("/building/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
        ).andExpect(status().isOk());
        assertEquals(0, buildingRepository.count());
    }

    @Test
    void deleteAsStudentShouldBeForbidden() throws Exception {
        Building saved = buildingRepository.save(building());
        mvc.perform(delete("/building/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
        ).andExpect(status().isForbidden());
        assertEquals(1, buildingRepository.count());
    }

    private Building building() {
        return Building.builder()
                .name("Name")
                .address("Address")
                .latitude(BigDecimal.valueOf(49.12345678))
                .longitude(BigDecimal.valueOf(24.98765432))
                .build();
    }

}
