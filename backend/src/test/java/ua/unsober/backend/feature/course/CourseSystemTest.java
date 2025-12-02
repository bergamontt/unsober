package ua.unsober.backend.feature.course;

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
import ua.unsober.backend.feature.subject.Subject;
import ua.unsober.backend.feature.subject.SubjectRepository;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.unsober.backend.common.EntityAsserts.assertCourse;
import static ua.unsober.backend.common.EntityAsserts.assertCourseArray;

class CourseSystemTest extends BaseSystemTest {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    CourseRequestMapper requestMapper;

    @Autowired
    CourseResponseMapper responseMapper;

    @AfterEach
    void tearDown() {
        courseRepository.deleteAll();
        subjectRepository.deleteAll();
    }

    @Test
    void createAsAdminShouldReturnNewCourse() throws Exception {
        Course course = course();
        CourseRequestDto requestDto = requestMapper.toDto(course);
        CourseResponseDto expected = responseMapper.toDto(course);
        ResultActions result = mvc.perform(post("/course")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isOk());
        assertCourse(result, expected);
        assertEquals(1, courseRepository.count());
    }

    @Test
    void createAsStudentShouldBeForbidden() throws Exception {
        CourseRequestDto requestDto = requestMapper.toDto(course());
        mvc.perform(post("/course")
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isForbidden());
        assertEquals(0, courseRepository.count());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getAllShouldReturnAllCourses(String token) throws Exception {
        Course saved = courseRepository.save(course());
        CourseResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/course")
                .header("Authorization", "Bearer " + token)
                .param("page", "0")
                .param("size", "10")
        ).andExpect(status().isOk());
        assertCourseArray(result, 0, expected);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getAllByYearShouldReturnFilteredCourses(String token) throws Exception {
        Course saved = courseRepository.save(course());
        CourseResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/course/year/{year}", saved.getCourseYear())
                .header("Authorization", "Bearer " + token)
                .param("page", "0")
                .param("size", "10")
        ).andExpect(status().isOk());
        assertCourseArray(result, 0, expected);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdShouldReturnCourse(String token) throws Exception {
        Course saved = courseRepository.save(course());
        CourseResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/course/{id}", saved.getId())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertCourse(result, expected);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdNonExistentShouldReturnBadRequest(String token) throws Exception {
        mvc.perform(get("/course/{id}", UUID.randomUUID())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void updateAsAdminShouldUpdateCourse() throws Exception {
        Course saved = courseRepository.save(course());
        saved.setMaxStudents(20);
        CourseRequestDto updated = requestMapper.toDto(saved);
        CourseResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(patch("/course/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updated))
        ).andExpect(status().isOk());
        assertCourse(result, expected);
    }

    @Test
    void updateAsStudentShouldBeForbidden() throws Exception {
        Course saved = courseRepository.save(course());
        CourseRequestDto updated = requestMapper.toDto(saved);
        mvc.perform(patch("/course/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updated))
        ).andExpect(status().isForbidden());
    }

    @Test
    void deleteAsAdminShouldRemoveCourse() throws Exception {
        Course saved = courseRepository.save(course());
        mvc.perform(delete("/course/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
        ).andExpect(status().isOk());
        assertEquals(0, courseRepository.count());
    }

    @Test
    void deleteAsStudentShouldBeForbidden() throws Exception {
        Course saved = courseRepository.save(course());
        mvc.perform(delete("/course/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
        ).andExpect(status().isForbidden());
        assertEquals(1, courseRepository.count());
    }

    private Subject createSubject() {
        return subjectRepository.save(
                Subject.builder()
                        .name("Algorithms")
                        .credits(BigDecimal.valueOf(6))
                        .hoursPerWeek(4)
                        .term(Term.AUTUMN)
                        .educationLevel(EducationLevel.BATCHELOR)
                        .build()
        );
    }

    private Course course() {
        return Course.builder()
                .subject(createSubject())
                .maxStudents(50)
                .numEnrolled(0)
                .courseYear(2025)
                .build();
    }

}
