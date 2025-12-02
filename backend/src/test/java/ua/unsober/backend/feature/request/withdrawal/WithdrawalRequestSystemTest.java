package ua.unsober.backend.feature.request.withdrawal;

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
import ua.unsober.backend.feature.studentenrollment.StudentEnrollment;
import ua.unsober.backend.feature.studentenrollment.StudentEnrollmentRepository;
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
import static ua.unsober.backend.common.EntityAsserts.assertWithdrawalRequest;
import static ua.unsober.backend.common.EntityAsserts.assertWithdrawalRequestArray;

class WithdrawalRequestSystemTest extends BaseSystemTest {

    @Autowired
    private WithdrawalRequestRepository requestRepository;

    @Autowired
    private WithdrawalRequestRequestMapper requestMapper;

    @Autowired
    private WithdrawalRequestResponseMapper responseMapper;

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

    @Autowired
    private StudentEnrollmentRepository enrollmentRepository;

    @AfterEach
    void tearDown() {
        requestRepository.deleteAll();
        enrollmentRepository.deleteAll();
        courseRepository.deleteAll();
        subjectRepository.deleteAll();
        studentRepository.deleteAll();
        specialityRepository.deleteAll();
        userRepository.deleteAll();
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void createShouldReturnNewRequest(String token) throws Exception {
        WithdrawalRequest request = withdrawalRequest();
        WithdrawalRequestRequestDto dto = requestMapper.toDto(request);
        WithdrawalRequestResponseDto expected = responseMapper.toDto(request);
        ResultActions result = mvc.perform(post("/withdrawal-request")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto))
        ).andExpect(status().isOk());
        assertWithdrawalRequest(result, expected);
        assertEquals(1, requestRepository.count());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getAllShouldReturnAllRequests(String token) throws Exception {
        WithdrawalRequest saved = requestRepository.save(withdrawalRequest());
        WithdrawalRequestResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/withdrawal-request")
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertWithdrawalRequestArray(result, 0, expected);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdShouldReturnRequest(String token) throws Exception {
        WithdrawalRequest saved = requestRepository.save(withdrawalRequest());
        WithdrawalRequestResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/withdrawal-request/{id}", saved.getId())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertWithdrawalRequest(result, expected);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdNonExistentShouldReturnBadRequest(String token) throws Exception {
        mvc.perform(get("/withdrawal-request/{id}", UUID.randomUUID())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getAllWithStatusShouldReturnFiltered(String token) throws Exception {
        WithdrawalRequest saved = requestRepository.save(withdrawalRequest());
        WithdrawalRequestResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/withdrawal-request/status/{status}", RequestStatus.PENDING)
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertWithdrawalRequestArray(result, 0, expected);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getAllByStudentIdShouldReturnFiltered(String token) throws Exception {
        WithdrawalRequest saved = requestRepository.save(withdrawalRequest());
        WithdrawalRequestResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/withdrawal-request/student/{studentId}",
                saved.getStudentEnrollment().getStudent().getId())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertWithdrawalRequestArray(result, 0, expected);
    }

    @Test
    void updateAsAdminShouldUpdateRequest() throws Exception {
        WithdrawalRequest saved = requestRepository.save(withdrawalRequest());
        saved.setReason("Updated reason");
        WithdrawalRequestRequestDto update = requestMapper.toDto(saved);
        WithdrawalRequestResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(patch("/withdrawal-request/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(update))
        ).andExpect(status().isOk());
        assertWithdrawalRequest(result, expected);
    }

    @Test
    void updateAsStudentShouldBeForbidden() throws Exception {
        WithdrawalRequest saved = requestRepository.save(withdrawalRequest());
        WithdrawalRequestRequestDto update = requestMapper.toDto(saved);
        mvc.perform(patch("/withdrawal-request/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(update))
        ).andExpect(status().isForbidden());
    }

    @Test
    void updateStatusAsAdminShouldUpdateStatus() throws Exception {
        WithdrawalRequest saved = requestRepository.save(withdrawalRequest());
        WithdrawalRequestResponseDto expected = responseMapper.toDto(saved);
        expected.setStatus(RequestStatus.ACCEPTED);
        ResultActions result = mvc.perform(patch("/withdrawal-request/update-status/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
                .param("status", "ACCEPTED")
        ).andExpect(status().isOk());
        assertWithdrawalRequest(result, expected);
    }

    @Test
    void updateStatusAsStudentShouldBeForbidden() throws Exception {
        WithdrawalRequest saved = requestRepository.save(withdrawalRequest());
        mvc.perform(patch("/withdrawal-request/update-status/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
                .param("status", "ACCEPTED")
        ).andExpect(status().isForbidden());
    }

    @Test
    void deleteAsAdminShouldDeleteRequest() throws Exception {
        WithdrawalRequest saved = requestRepository.save(withdrawalRequest());
        mvc.perform(delete("/withdrawal-request/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
        ).andExpect(status().isOk());
        assertEquals(0, requestRepository.count());
    }

    @Test
    void deleteAsStudentShouldBeForbidden() throws Exception {
        WithdrawalRequest saved = requestRepository.save(withdrawalRequest());
        mvc.perform(delete("/withdrawal-request/{id}", saved.getId())
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
                .email("email@example.com")
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
        return enrollmentRepository.save(StudentEnrollment.builder()
                .student(student())
                .course(course())
                .status(EnrollmentStatus.ENROLLED)
                .enrollmentYear(2025)
                .build());
    }

    private WithdrawalRequest withdrawalRequest() {
        return WithdrawalRequest.builder()
                .studentEnrollment(enrollment())
                .reason("Reason")
                .status(RequestStatus.PENDING)
                .build();
    }

}
