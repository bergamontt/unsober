package ua.unsober.backend.feature.studentenrollment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ua.unsober.backend.common.BaseSystemTest;
import ua.unsober.backend.common.enums.*;
import ua.unsober.backend.feature.course.Course;
import ua.unsober.backend.feature.course.CourseRepository;
import ua.unsober.backend.feature.coursegroup.CourseGroupRepository;
import ua.unsober.backend.feature.speciality.Speciality;
import ua.unsober.backend.feature.speciality.SpecialityRepository;
import ua.unsober.backend.feature.student.Student;
import ua.unsober.backend.feature.student.StudentRepository;
import ua.unsober.backend.feature.subject.Subject;
import ua.unsober.backend.feature.subject.SubjectRepository;
import ua.unsober.backend.feature.user.User;
import ua.unsober.backend.feature.user.UserRepository;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.unsober.backend.common.EntityAsserts.*;

class StudentEnrollmentSystemTest extends BaseSystemTest {

    @Autowired
    private StudentEnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentEnrollmentRequestMapper requestMapper;

    @Autowired
    private StudentEnrollmentResponseMapper responseMapper;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    private CourseGroupRepository groupRepository;

    @AfterEach
    void tearDown() {
        enrollmentRepository.deleteAll();
        courseRepository.deleteAll();
        subjectRepository.deleteAll();
        studentRepository.deleteAll();
        specialityRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void createAsAdminShouldReturnNewEnrollment() throws Exception {
        StudentEnrollment enrollment = enrollment();
        StudentEnrollmentRequestDto requestDto = requestMapper.toDto(enrollment);
        StudentEnrollmentResponseDto expected = responseMapper.toDto(enrollment);
        ResultActions result = mvc.perform(post("/student-enrollment")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isOk());
        expected.getCourse().setNumEnrolled(1);
        assertStudentEnrollment(result, expected);
        assertEquals(1, enrollmentRepository.count());
    }

    @Test
    void createAsStudentShouldBeForbidden() throws Exception {
        StudentEnrollmentRequestDto requestDto = requestMapper.toDto(enrollment());
        mvc.perform(post("/student-enrollment")
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isForbidden());
        assertEquals(0, enrollmentRepository.count());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getAllShouldReturnAllEnrollments(String token) throws Exception {
        StudentEnrollment saved = enrollmentRepository.save(enrollment());
        StudentEnrollmentResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/student-enrollment")
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertStudentEnrollmentArray(result, 0, expected);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdShouldReturnEnrollment(String token) throws Exception {
        StudentEnrollment saved = enrollmentRepository.save(enrollment());
        StudentEnrollmentResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/student-enrollment/{id}", saved.getId())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertStudentEnrollment(result, expected);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdNonExistentShouldReturnBadRequest(String token) throws Exception {
        mvc.perform(get("/student-enrollment/{id}", UUID.randomUUID())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void existsByStudentAndCourseShouldReturnTrue(String token) throws Exception {
        StudentEnrollment saved = enrollmentRepository.save(enrollment());
        mvc.perform(get("/student-enrollment/exists")
                        .header("Authorization", "Bearer " + token)
                        .param("studentId", saved.getStudent().getId().toString())
                        .param("courseId", saved.getCourse().getId().toString())
                ).andExpect(status().isOk())
                .andExpect(result -> assertEquals("true", result.getResponse().getContentAsString()));
    }

    @Test
    void updateAsAdminShouldUpdateEnrollment() throws Exception {
        StudentEnrollment saved = enrollmentRepository.save(enrollment());
        saved.setEnrollmentYear(2026);
        StudentEnrollmentRequestDto updated = requestMapper.toDto(saved);
        StudentEnrollmentResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(patch("/student-enrollment/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updated))
        ).andExpect(status().isOk());
        assertStudentEnrollment(result, expected);
    }

    @Test
    void updateAsStudentShouldBeForbidden() throws Exception {
        StudentEnrollment saved = enrollmentRepository.save(enrollment());
        StudentEnrollmentRequestDto updated = requestMapper.toDto(saved);
        mvc.perform(patch("/student-enrollment/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updated))
        ).andExpect(status().isForbidden());
    }

    @Test
    void deleteAsAdminShouldDeleteEnrollment() throws Exception {
        StudentEnrollment saved = enrollmentRepository.save(enrollment());
        saved.getCourse().setNumEnrolled(1);
        mvc.perform(delete("/student-enrollment/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
        ).andExpect(status().isOk());
        StudentEnrollment withdrawn = enrollmentRepository.findById(saved.getId()).orElseThrow();
        assertEquals(EnrollmentStatus.WITHDRAWN, withdrawn.getStatus());
        assertEquals(1, enrollmentRepository.count());
    }

    @Test
    void deleteAsStudentShouldBeForbidden() throws Exception {
        StudentEnrollment saved = enrollmentRepository.save(enrollment());
        mvc.perform(delete("/student-enrollment/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
        ).andExpect(status().isForbidden());
        assertEquals(1, enrollmentRepository.count());
    }

    private Speciality speciality() {
        return specialityRepository.save(Speciality.builder()
                .name("Speciality")
                .description("Description")
                .build());
    }

    private User user() {
        return userRepository.save(User.builder()
                .firstName("Name")
                .lastName("Last name")
                .patronymic("Patronymic")
                .role(Role.STUDENT)
                .email("e@example.com")
                .passwordHash("a".repeat(60))
                .build());
    }

    private Student student() {
        return studentRepository.save(Student.builder()
                .user(user())
                .speciality(speciality())
                .recordBookNumber("RB123")
                .studyYear(2)
                .status(StudentStatus.STUDYING)
                .build());
    }

    private Subject subject() {
        return subjectRepository.save(Subject.builder()
                .name("Name")
                .credits(BigDecimal.valueOf(6))
                .hoursPerWeek(4)
                .term(Term.AUTUMN)
                .educationLevel(EducationLevel.BATCHELOR)
                .build());
    }

    private Course course() {
        return courseRepository.save(Course.builder()
                .subject(subject())
                .maxStudents(50)
                .numEnrolled(0)
                .courseYear(2025)
                .build());
    }

    private StudentEnrollment enrollment() {
        return StudentEnrollment.builder()
                .student(student())
                .course(course())
                .status(EnrollmentStatus.ENROLLED)
                .enrollmentYear(2025)
                .build();
    }

}
