package ua.unsober.backend.feature.coursegroup;

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
import ua.unsober.backend.feature.course.Course;
import ua.unsober.backend.feature.course.CourseRepository;
import ua.unsober.backend.feature.subject.Subject;
import ua.unsober.backend.feature.subject.SubjectRepository;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.unsober.backend.common.EntityAsserts.assertCourseGroup;
import static ua.unsober.backend.common.EntityAsserts.assertCourseGroupArray;

class CourseGroupSystemTest extends BaseSystemTest {

    @Autowired
    CourseGroupRepository groupRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    CourseGroupRequestMapper requestMapper;

    @Autowired
    CourseGroupResponseMapper responseMapper;

    @AfterEach
    void tearDown() {
        groupRepository.deleteAll();
        courseRepository.deleteAll();
        subjectRepository.deleteAll();
    }

    @Test
    void createAsAdminShouldReturnNewGroup() throws Exception {
        CourseGroup group = courseGroup();
        CourseGroupRequestDto requestDto = requestMapper.toDto(group);
        CourseGroupResponseDto expected = responseMapper.toDto(group);
        ResultActions result = mvc.perform(post("/course-group")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isOk());
        assertCourseGroup(result, expected);
        assertEquals(1, groupRepository.count());
    }

    @Test
    void createAsStudentShouldBeForbidden() throws Exception {
        CourseGroupRequestDto requestDto = requestMapper.toDto(courseGroup());
        mvc.perform(post("/course-group")
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isForbidden());
        assertEquals(0, groupRepository.count());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getAllShouldReturnAllGroups(String token) throws Exception {
        CourseGroup saved = groupRepository.save(courseGroup());
        CourseGroupResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/course-group")
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertCourseGroupArray(result, 0, expected);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getAllByCourseIdShouldReturnGroups(String token) throws Exception {
        CourseGroup saved = groupRepository.save(courseGroup());
        CourseGroupResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/course-group/course/{courseId}", saved.getCourse().getId())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertCourseGroupArray(result, 0, expected);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdShouldReturnGroupWithGivenId(String token) throws Exception {
        CourseGroup saved = groupRepository.save(courseGroup());
        CourseGroupResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/course-group/{id}", saved.getId())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertCourseGroup(result, expected);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdNonExistentShouldReturnBadRequest(String token) throws Exception {
        mvc.perform(get("/course-group/{id}", UUID.randomUUID())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void updateAsAdminShouldUpdateGroup() throws Exception {
        CourseGroup saved = groupRepository.save(courseGroup());
        saved.setMaxStudents(saved.getMaxStudents() + 5);
        CourseGroupRequestDto updatedDto = requestMapper.toDto(saved);
        CourseGroupResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(patch("/course-group/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedDto))
        ).andExpect(status().isOk());
        assertCourseGroup(result, expected);
    }

    @Test
    void updateAsStudentShouldBeForbidden() throws Exception {
        CourseGroup saved = groupRepository.save(courseGroup());
        CourseGroupRequestDto updatedDto = requestMapper.toDto(saved);
        mvc.perform(patch("/course-group/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedDto))
        ).andExpect(status().isForbidden());
    }

    @Test
    void deleteAsAdminShouldRemoveGroup() throws Exception {
        CourseGroup saved = groupRepository.save(courseGroup());
        mvc.perform(delete("/course-group/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
        ).andExpect(status().isOk());
        assertEquals(0, groupRepository.count());
    }

    @Test
    void deleteAsStudentShouldFail() throws Exception {
        CourseGroup saved = groupRepository.save(courseGroup());
        mvc.perform(delete("/course-group/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
        ).andExpect(status().isForbidden());
        assertEquals(1, groupRepository.count());
    }

    private Subject subject() {
        return subjectRepository.save(Subject.builder()
                .name("Name")
                .credits(BigDecimal.valueOf(3))
                .hoursPerWeek(3)
                .educationLevel(EducationLevel.BATCHELOR)
                .term(Term.AUTUMN)
                .build());
    }

    private Course course() {
        return courseRepository.save(Course.builder()
                .subject(subject())
                .courseYear(2025)
                .maxStudents(20)
                .numEnrolled(0)
                .build());
    }

    private CourseGroup courseGroup() {
        return CourseGroup.builder()
                .course(course())
                .groupNumber(1)
                .maxStudents(10)
                .numEnrolled(0)
                .build();
    }

}