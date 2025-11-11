package ua.unsober.backend.feature.course;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.unsober.backend.common.SecurityTestConfig;

import java.util.List;
import java.util.UUID;

@WebMvcTest(CourseController.class)
@Import(SecurityTestConfig.class)
public class CourseControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private CourseService courseService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void createShouldReturnSameInsertedCourse() throws Exception {
        UUID subjectId = UUID.randomUUID();
        CourseRequestDto request = CourseRequestDto.builder()
                .subjectId(subjectId)
                .maxStudents(60)
                .courseYear(2025)
                .build();
        CourseResponseDto response = CourseResponseDto.builder()
                .id(UUID.randomUUID())
                .maxStudents(request.getMaxStudents())
                .courseYear(request.getCourseYear())
                .build();
        when(courseService.create(any())).thenReturn(response);
        mvc.perform(post("/course")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId().toString()))
                .andExpect(jsonPath("$.maxStudents").value(response.getMaxStudents()))
                .andExpect(jsonPath("$.courseYear").value(response.getCourseYear()));
    }

    @Test
    void getAllShouldReturnAllExistingCourses() throws Exception {
        CourseResponseDto course = CourseResponseDto.builder()
                .id(UUID.randomUUID())
                .maxStudents(60)
                .courseYear(2025)
                .build();
        when(courseService.getAll()).thenReturn(List.of(course));
        mvc.perform(get("/course"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(course.getId().toString()))
                .andExpect(jsonPath("$[0].maxStudents").value(course.getMaxStudents()))
                .andExpect(jsonPath("$[0].courseYear").value(course.getCourseYear()));
    }

    @Test
    void getByIdShouldReturnCourseWithGivenId() throws Exception {
        UUID id = UUID.randomUUID();
        CourseResponseDto response = CourseResponseDto.builder()
                .id(id)
                .build();
        when(courseService.getById(id)).thenReturn(response);
        mvc.perform(get("/course/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void updateShouldReturnModifiedCourse() throws Exception {
        UUID id = UUID.randomUUID();
        CourseRequestDto request = CourseRequestDto.builder()
                .subjectId(id)
                .maxStudents(200)
                .courseYear(2025)
                .build();
        CourseResponseDto response = CourseResponseDto.builder()
                .id(id)
                .maxStudents(request.getMaxStudents())
                .courseYear(request.getCourseYear())
                .build();
        when(courseService.update(id, request)).thenReturn(response);
        mvc.perform(patch("/course/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId().toString()))
                .andExpect(jsonPath("$.maxStudents").value(response.getMaxStudents()))
                .andExpect(jsonPath("$.courseYear").value(response.getCourseYear()));
    }

    @Test
    void deleteShouldReturnOkStatus() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(courseService).delete(id);
        mvc.perform(delete("/course/{id}", id))
                .andExpect(status().isOk());
    }

}