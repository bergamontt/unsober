package ua.unsober.backend.feature.speciality;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ua.unsober.backend.common.BaseSystemTest;
import ua.unsober.backend.feature.department.Department;
import ua.unsober.backend.feature.department.DepartmentRepository;
import ua.unsober.backend.feature.faculty.Faculty;
import ua.unsober.backend.feature.faculty.FacultyRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.unsober.backend.common.EntityAsserts.assertSpeciality;
import static ua.unsober.backend.common.EntityAsserts.assertSpecialityArray;

class SpecialitySystemTest extends BaseSystemTest {

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private SpecialityRequestMapper requestMapper;

    @Autowired
    private SpecialityResponseMapper responseMapper;

    @AfterEach
    void tearDown() {
        specialityRepository.deleteAll();
        departmentRepository.deleteAll();
        facultyRepository.deleteAll();
    }

    @Test
    void createAsAdminShouldReturnNewSpeciality() throws Exception {
        Speciality speciality = speciality();
        SpecialityRequestDto requestDto = requestMapper.toDto(speciality);
        SpecialityResponseDto responseDto = responseMapper.toDto(speciality);
        ResultActions result = mvc.perform(post("/speciality")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isOk());
        assertSpeciality(result, responseDto);
        assertEquals(1, specialityRepository.count());
    }

    @Test
    void createAsStudentShouldReturnForbidden() throws Exception {
        SpecialityRequestDto requestDto = requestMapper.toDto(speciality());
        mvc.perform(post("/speciality")
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isForbidden());
        assertEquals(0, specialityRepository.count());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getAllShouldReturnAllSpecialities(String token) throws Exception {
        Speciality saved = specialityRepository.save(speciality());
        SpecialityResponseDto expectedDto = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/speciality")
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertSpecialityArray(result, 0, expectedDto);
        assertEquals(1, specialityRepository.count());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdShouldReturnSpeciality(String token) throws Exception {
        Speciality saved = specialityRepository.save(speciality());
        SpecialityResponseDto expectedDto = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/speciality/{id}", saved.getId())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertSpeciality(result, expectedDto);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdNonExistentShouldReturnBadRequest(String token) throws Exception {
        mvc.perform(get("/speciality/{id}", UUID.randomUUID())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void updateAsAdminShouldModifySpeciality() throws Exception {
        Speciality saved = specialityRepository.save(speciality());
        saved.setName("Updated");
        SpecialityRequestDto requestDto = requestMapper.toDto(saved);
        SpecialityResponseDto responseDto = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(patch("/speciality/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isOk());
        assertSpeciality(result, responseDto);
    }

    @Test
    void updateAsStudentShouldReturnForbidden() throws Exception {
        Speciality saved = specialityRepository.save(speciality());
        SpecialityRequestDto requestDto = requestMapper.toDto(saved);
        mvc.perform(patch("/speciality/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isForbidden());
    }

    @Test
    void deleteAsAdminShouldRemoveSpeciality() throws Exception {
        Speciality saved = specialityRepository.save(speciality());
        mvc.perform(delete("/speciality/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
        ).andExpect(status().isOk());
        assertEquals(0, specialityRepository.count());
    }

    @Test
    void deleteAsStudentShouldReturnForbidden() throws Exception {
        Speciality saved = specialityRepository.save(speciality());
        mvc.perform(delete("/speciality/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
        ).andExpect(status().isForbidden());
        assertEquals(1, specialityRepository.count());
    }

    private Faculty faculty() {
        Faculty faculty = Faculty.builder()
                .name("Name")
                .description("Description")
                .build();
        return facultyRepository.saveAndFlush(faculty);
    }

    private Department department() {
        Department department = Department.builder()
                .name("Name")
                .description("Description")
                .faculty(faculty())
                .build();
        return departmentRepository.saveAndFlush(department);
    }

    private Speciality speciality() {
        return Speciality.builder()
                .department(department())
                .name("Name")
                .description("Description")
                .build();
    }

}
