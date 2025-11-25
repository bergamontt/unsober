package ua.unsober.backend.feature.course;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ua.unsober.backend.common.enums.Role;
import ua.unsober.backend.common.enums.Term;
import ua.unsober.backend.feature.auth.JwtTokenService;
import ua.unsober.backend.feature.subject.Subject;
import ua.unsober.backend.feature.subject.SubjectRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CourseControllerSystemTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private JwtTokenService jwtService;

    private String studentToken;
    private String adminToken;

    private Subject subject;
    private Course course;

    @BeforeEach
    void setUp() {
        studentToken = jwtService.generateToken(
                new UsernamePasswordAuthenticationToken(
                        "student", null, List.of(new SimpleGrantedAuthority(Role.STUDENT.name()))
                )
        );
        adminToken = jwtService.generateToken(
                new UsernamePasswordAuthenticationToken(
                        "admin", null, List.of(new SimpleGrantedAuthority(Role.ADMIN.name()))
                )
        );
        subject = Subject.builder()
                .name("Test Subject")
                .credits(BigDecimal.valueOf(3))
                .term(Term.AUTUMN)
                .build();
        subject = subjectRepository.save(subject);
        course = Course.builder()
                .subject(subject)
                .maxStudents(30)
                .courseYear(2025)
                .numEnrolled(0)
                .build();
    }

    @AfterEach
    void tearDown() {
        courseRepository.deleteAll();
        subjectRepository.deleteAll();
    }

    @Test
    void createAsAdminShouldSaveAndReturnCourse() throws Exception {
        CourseRequestDto request = CourseRequestDto.builder()
                .subjectId(subject.getId())
                .maxStudents(course.getMaxStudents())
                .courseYear(course.getCourseYear())
                .build();
        mvc.perform(post("/course")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maxStudents").value(request.getMaxStudents()))
                .andExpect(jsonPath("$.courseYear").value(request.getCourseYear()));
        assertEquals(1, courseRepository.count());
    }

    @Test
    void createAsStudentShouldFail() throws Exception {
        CourseRequestDto request = CourseRequestDto.builder()
                .subjectId(subject.getId())
                .maxStudents(course.getMaxStudents())
                .courseYear(course.getCourseYear())
                .build();
        mvc.perform(post("/course")
                        .header("Authorization", "Bearer " + studentToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
        assertEquals(0, courseRepository.count());
    }

    @Test
    void getAllAsStudentShouldReturnAllCourses() throws Exception {
        courseRepository.save(course);
        mvc.perform(get("/course")
                        .header("Authorization", "Bearer " + studentToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].maxStudents").value(course.getMaxStudents()))
                .andExpect(jsonPath("$[0].courseYear").value(course.getCourseYear()));
    }

    @Test
    void getAllAsAdminShouldReturnAllCourses() throws Exception {
        courseRepository.save(course);
        mvc.perform(get("/course")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].maxStudents").value(course.getMaxStudents()))
                .andExpect(jsonPath("$[0].courseYear").value(course.getCourseYear()));
    }

    @Test
    void getByIdAsStudentShouldReturnCourse() throws Exception {
        Course saved = courseRepository.save(course);
        mvc.perform(get("/course/{id}", saved.getId())
                        .header("Authorization", "Bearer " + studentToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maxStudents").value(saved.getMaxStudents()))
                .andExpect(jsonPath("$.courseYear").value(saved.getCourseYear()));
    }

    @Test
    void getByIdAsAdminShouldReturnCourse() throws Exception {
        Course saved = courseRepository.save(course);
        mvc.perform(get("/course/{id}", saved.getId())
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maxStudents").value(saved.getMaxStudents()))
                .andExpect(jsonPath("$.courseYear").value(saved.getCourseYear()));
    }

    @Test
    void updateAsAdminShouldModifyCourse() throws Exception {
        Course saved = courseRepository.save(course);
        CourseRequestDto dto = CourseRequestDto.builder()
                .subjectId(subject.getId())
                .maxStudents(50)
                .courseYear(2026)
                .build();
        mvc.perform(patch("/course/{id}", saved.getId())
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maxStudents").value(dto.getMaxStudents()))
                .andExpect(jsonPath("$.courseYear").value(dto.getCourseYear()));
        Course updated = courseRepository.findById(saved.getId()).orElseThrow();
        assertEquals(updated.getMaxStudents(), dto.getMaxStudents());
        assertEquals(updated.getCourseYear(), dto.getCourseYear());
    }

    @Test
    void updateAsStudentShouldFail() throws Exception {
        Course savedCourse = courseRepository.save(course);
        CourseRequestDto dto = CourseRequestDto.builder()
                .subjectId(subject.getId())
                .maxStudents(50)
                .courseYear(2026)
                .build();
        mvc.perform(patch("/course/{id}", savedCourse.getId())
                        .header("Authorization", "Bearer " + studentToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
        Course updated = courseRepository.findById(savedCourse.getId()).orElseThrow();
        assertEquals(course.getMaxStudents(), updated.getMaxStudents());
        assertEquals(course.getCourseYear(), updated.getCourseYear());
    }

    @Test
    void deleteAsAdminShouldRemoveCourse() throws Exception {
        Course saved = courseRepository.save(course);
        mvc.perform(delete("/course/{id}", saved.getId())
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
        assertEquals(0, courseRepository.count());
    }

    @Test
    void deleteAsStudentShouldFail() throws Exception {
        Course saved = courseRepository.save(course);
        mvc.perform(delete("/course/{id}", saved.getId())
                        .header("Authorization", "Bearer " + studentToken))
                .andExpect(status().isForbidden());
        assertEquals(1, courseRepository.count());
    }

}