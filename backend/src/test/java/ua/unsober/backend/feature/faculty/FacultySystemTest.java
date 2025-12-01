package ua.unsober.backend.feature.faculty;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ua.unsober.backend.common.BaseSystemTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.unsober.backend.common.EntityAsserts.assertFaculty;
import static ua.unsober.backend.common.EntityAsserts.assertFacultyArray;

class FacultySystemTest extends BaseSystemTest {

    @Autowired
    FacultyRepository facultyRepository;

    @Autowired
    FacultyRequestMapper requestMapper;

    @Autowired
    FacultyResponseMapper responseMapper;

    @AfterEach
    void tearDown() {
        facultyRepository.deleteAll();
    }

    @Test
    void createAsAdminShouldReturnNewFaculty() throws Exception {
        Faculty faculty = faculty();
        FacultyRequestDto requestDto = requestMapper.toDto(faculty);
        FacultyResponseDto responseDto = responseMapper.toDto(faculty);
        ResultActions result = mvc.perform(post("/faculty")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isOk());
        assertFaculty(result, responseDto);
        assertEquals(1, facultyRepository.count());
    }

    @Test
    void createAsStudentShouldReturnForbidden() throws Exception {
        FacultyRequestDto requestDto = requestMapper.toDto(faculty());
        mvc.perform(post("/faculty")
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isForbidden());
        assertEquals(0, facultyRepository.count());
    }

    @Test
    void getAllAsAdminShouldReturnAllFaculties() throws Exception {
        Faculty saved = facultyRepository.save(faculty());
        FacultyResponseDto expectedDto = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/faculty")
                .header("Authorization", "Bearer " + adminToken)
        ).andExpect(status().isOk());
        assertFacultyArray(result, 0, expectedDto);
        assertEquals(1, facultyRepository.count());
    }

    @Test
    void getAllAsStudentShouldReturnAllFaculties() throws Exception {
        Faculty saved = facultyRepository.save(faculty());
        FacultyResponseDto expectedDto = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/faculty")
                .header("Authorization", "Bearer " + studentToken)
        ).andExpect(status().isOk());
        assertFacultyArray(result, 0, expectedDto);
        assertEquals(1, facultyRepository.count());
    }

    @Test
    void getByIdAsAdminShouldReturnFacultyWithGivenId() throws Exception {
        Faculty saved = facultyRepository.save(faculty());
        FacultyResponseDto expectedDto = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/faculty/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
        ).andExpect(status().isOk());
        assertFaculty(result, expectedDto);
    }

    @Test
    void getByIdAsStudentShouldReturnFaculty() throws Exception {
        Faculty saved = facultyRepository.save(faculty());
        FacultyResponseDto expectedDto = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/faculty/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
        ).andExpect(status().isOk());
        assertFaculty(result, expectedDto);
    }

    @Test
    void getByIdNonExistentShouldReturnBadRequest() throws Exception {
        mvc.perform(get("/faculty/{id}", UUID.randomUUID())
                .header("Authorization", "Bearer " + adminToken)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void updateAsAdminShouldModifyFaculty() throws Exception {
        Faculty saved = facultyRepository.save(faculty());
        saved.setName("Updated");
        FacultyRequestDto requestDto = requestMapper.toDto(saved);
        FacultyResponseDto responseDto = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(patch("/faculty/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isOk());
        assertFaculty(result, responseDto);
    }

    @Test
    void updateAsStudentShouldReturnForbidden() throws Exception {
        Faculty saved = facultyRepository.save(faculty());
        FacultyRequestDto requestDto = requestMapper.toDto(saved);
        mvc.perform(patch("/faculty/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isForbidden());
    }

    @Test
    void deleteAsAdminShouldRemoveFaculty() throws Exception {
        Faculty saved = facultyRepository.save(faculty());
        mvc.perform(delete("/faculty/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
        ).andExpect(status().isOk());
        assertEquals(0, facultyRepository.count());
    }

    @Test
    void deleteAsStudentShouldReturnForbidden() throws Exception {
        Faculty saved = facultyRepository.save(faculty());
        mvc.perform(delete("/faculty/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
        ).andExpect(status().isForbidden());
    }

    private Faculty faculty() {
        return Faculty.builder()
                .name("Name")
                .description("Description")
                .build();
    }

}
