package ua.unsober.backend.feature.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.unsober.backend.common.TestSecurityConfig;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
@Import(TestSecurityConfig.class)
public class StudentControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void createShouldReturnInsertedStudent() throws Exception {
        UUID specialityId = UUID.randomUUID();
        StudentRequestDto request = StudentRequestDto.builder()
                .firstName("Name")
                .lastName("Surname")
                .patronymic("Middle")
                .recordBookNumber("Number")
                .email("name@example.com")
                .password("secret")
                .specialityId(specialityId)
                .studyYear(2)
                .build();
        StudentResponseDto response = StudentResponseDto.builder()
                .id(UUID.randomUUID())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .patronymic(request.getPatronymic())
                .recordBookNumber(request.getRecordBookNumber())
                .email(request.getEmail())
                .studyYear(request.getStudyYear())
                .build();
        when(studentService.create(any())).thenReturn(response);
        mvc.perform(post("/student")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value(response.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(response.getLastName()))
                .andExpect(jsonPath("$.patronymic").value(response.getPatronymic()))
                .andExpect(jsonPath("$.recordBookNumber").value(response.getRecordBookNumber()))
                .andExpect(jsonPath("$.email").value(response.getEmail()))
                .andExpect(jsonPath("$.studyYear").value(response.getStudyYear()));
    }

    @Test
    void getAllShouldReturnAllExistingStudents() throws Exception {
        StudentResponseDto student = StudentResponseDto.builder()
                .id(UUID.randomUUID())
                .firstName("Name")
                .lastName("Surname")
                .build();
        when(studentService.getAll()).thenReturn(List.of(student));
        mvc.perform(get("/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(student.getId().toString()))
                .andExpect(jsonPath("$[0].firstName").value(student.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(student.getLastName()));
    }

    @Test
    void getByIdShouldReturnStudentWithTheGivenId() throws Exception {
        UUID id = UUID.randomUUID();
        StudentResponseDto response = StudentResponseDto.builder()
                .id(id)
                .build();
        when(studentService.getById(id)).thenReturn(response);
        mvc.perform(get("/student/uuid/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId().toString()));
    }

    @Test
    void updateShouldReturnModifiedStudent() throws Exception {
        UUID id = UUID.randomUUID();
        UUID specialityId = UUID.randomUUID();
        StudentRequestDto request = StudentRequestDto.builder()
                .firstName("New Name")
                .lastName("New Surname")
                .patronymic("New Middle")
                .recordBookNumber("New Number")
                .email("newname@example.com")
                .password("secret")
                .specialityId(specialityId)
                .studyYear(2)
                .build();
        StudentResponseDto response = StudentResponseDto.builder()
                .id(UUID.randomUUID())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .patronymic(request.getPatronymic())
                .recordBookNumber(request.getRecordBookNumber())
                .email(request.getEmail())
                .studyYear(request.getStudyYear())
                .build();
        when(studentService.update(id, request)).thenReturn(response);
        mvc.perform(patch("/student/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId().toString()))
                .andExpect(jsonPath("$.firstName").value(response.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(response.getLastName()))
                .andExpect(jsonPath("$.patronymic").value(response.getPatronymic()))
                .andExpect(jsonPath("$.recordBookNumber").value(response.getRecordBookNumber()))
                .andExpect(jsonPath("$.email").value(response.getEmail()))
                .andExpect(jsonPath("$.studyYear").value(response.getStudyYear()));
    }

    @Test
    void deleteShouldReturnOkStatus() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(studentService).delete(id);
        mvc.perform(delete("/student/{id}", id))
                .andExpect(status().isOk());
    }
}