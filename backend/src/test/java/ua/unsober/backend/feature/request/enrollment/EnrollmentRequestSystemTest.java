package ua.unsober.backend.feature.request.enrollment;

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
import ua.unsober.backend.feature.student.Student;
import ua.unsober.backend.feature.student.StudentRepository;
import ua.unsober.backend.feature.subject.Subject;
import ua.unsober.backend.feature.subject.SubjectRepository;
import ua.unsober.backend.feature.user.User;
import ua.unsober.backend.feature.user.UserRepository;
import ua.unsober.backend.feature.speciality.Speciality;
import ua.unsober.backend.feature.speciality.SpecialityRepository;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.unsober.backend.common.EntityAsserts.assertEnrollmentRequest;
import static ua.unsober.backend.common.EntityAsserts.assertEnrollmentRequestArray;

class EnrollmentRequestSystemTest extends BaseSystemTest {

    @Autowired
    private EnrollmentRequestRepository requestRepository;

    @Autowired
    private EnrollmentRequestRequestMapper requestMapper;

    @Autowired
    private EnrollmentRequestResponseMapper responseMapper;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @AfterEach
    void tearDown() {
        requestRepository.deleteAll();
        courseRepository.deleteAll();
        subjectRepository.deleteAll();
        studentRepository.deleteAll();
        specialityRepository.deleteAll();
        userRepository.deleteAll();
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void createShouldReturnNewRequest(String token) throws Exception {
        EnrollmentRequest request = enrollmentRequest();
        EnrollmentRequestRequestDto requestDto = requestMapper.toDto(request);
        EnrollmentRequestResponseDto expected = responseMapper.toDto(request);
        ResultActions result = mvc.perform(post("/enrollment-request")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isOk());
        assertEnrollmentRequest(result, expected);
        assertEquals(1, requestRepository.count());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getAllShouldReturnAllRequests(String token) throws Exception {
        EnrollmentRequest saved = requestRepository.save(enrollmentRequest());
        EnrollmentRequestResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/enrollment-request")
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertEnrollmentRequestArray(result, 0, expected);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdShouldReturnRequest(String token) throws Exception {
        EnrollmentRequest saved = requestRepository.save(enrollmentRequest());
        EnrollmentRequestResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/enrollment-request/{id}", saved.getId())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertEnrollmentRequest(result, expected);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdNonExistentShouldReturnBadRequest(String token) throws Exception {
        mvc.perform(get("/enrollment-request/{id}", UUID.randomUUID())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getAllWithStatusShouldReturnFilteredRequests(String token) throws Exception {
        EnrollmentRequest saved = requestRepository.save(enrollmentRequest());
        EnrollmentRequestResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/enrollment-request/status/{status}", RequestStatus.PENDING)
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertEnrollmentRequestArray(result, 0, expected);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getAllByStudentIdShouldReturnFiltered(String token) throws Exception {
        EnrollmentRequest saved = requestRepository.save(enrollmentRequest());
        EnrollmentRequestResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/enrollment-request/student/{id}", saved.getStudent().getId())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertEnrollmentRequestArray(result, 0, expected);
    }

    @Test
    void updateAsAdminShouldUpdateRequest() throws Exception {
        EnrollmentRequest saved = requestRepository.save(enrollmentRequest());
        saved.setReason("Updated reason");
        EnrollmentRequestRequestDto updated = requestMapper.toDto(saved);
        EnrollmentRequestResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(patch("/enrollment-request/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updated))
        ).andExpect(status().isOk());
        assertEnrollmentRequest(result, expected);
    }

    @Test
    void updateAsStudentShouldBeForbidden() throws Exception {
        EnrollmentRequest saved = requestRepository.save(enrollmentRequest());
        EnrollmentRequestRequestDto updated = requestMapper.toDto(saved);
        mvc.perform(patch("/enrollment-request/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updated))
        ).andExpect(status().isForbidden());
    }

    @Test
    void updateStatusAsAdminShouldUpdateStatus() throws Exception {
        EnrollmentRequest saved = requestRepository.save(enrollmentRequest());
        EnrollmentRequestResponseDto expected = responseMapper.toDto(saved);
        expected.setStatus(RequestStatus.ACCEPTED);
        ResultActions result = mvc.perform(patch("/enrollment-request/update-status/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
                .param("status", "ACCEPTED")
        ).andExpect(status().isOk());
        assertEnrollmentRequest(result, expected);
    }

    @Test
    void updateStatusAsStudentShouldBeForbidden() throws Exception {
        EnrollmentRequest saved = requestRepository.save(enrollmentRequest());
        mvc.perform(patch("/enrollment-request/update-status/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
                .param("status", "APPROVED")
        ).andExpect(status().isForbidden());
    }

    @Test
    void deleteAsAdminShouldDeleteRequest() throws Exception {
        EnrollmentRequest saved = requestRepository.save(enrollmentRequest());
        mvc.perform(delete("/enrollment-request/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
        ).andExpect(status().isOk());
        assertEquals(0, requestRepository.count());
    }

    @Test
    void deleteAsStudentShouldBeForbidden() throws Exception {
        EnrollmentRequest saved = requestRepository.save(enrollmentRequest());
        mvc.perform(delete("/enrollment-request/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
        ).andExpect(status().isForbidden());
        assertEquals(1, requestRepository.count());
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

    private EnrollmentRequest enrollmentRequest() {
        return EnrollmentRequest.builder()
                .student(student())
                .course(course())
                .reason("Reason")
                .status(RequestStatus.PENDING)
                .build();
    }

}
