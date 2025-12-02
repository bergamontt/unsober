package ua.unsober.backend.feature.courseclass;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ua.unsober.backend.common.BaseSystemTest;
import ua.unsober.backend.common.enums.ClassType;
import ua.unsober.backend.common.enums.EducationLevel;
import ua.unsober.backend.common.enums.Term;
import ua.unsober.backend.common.enums.WeekDay;
import ua.unsober.backend.feature.building.Building;
import ua.unsober.backend.feature.building.BuildingRepository;
import ua.unsober.backend.feature.course.Course;
import ua.unsober.backend.feature.course.CourseRepository;
import ua.unsober.backend.feature.coursegroup.CourseGroup;
import ua.unsober.backend.feature.coursegroup.CourseGroupRepository;
import ua.unsober.backend.feature.subject.Subject;
import ua.unsober.backend.feature.subject.SubjectRepository;
import ua.unsober.backend.feature.teacher.Teacher;
import ua.unsober.backend.feature.teacher.TeacherRepository;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.unsober.backend.common.EntityAsserts.assertCourseClass;
import static ua.unsober.backend.common.EntityAsserts.assertCourseClassArray;

class CourseClassSystemTest extends BaseSystemTest {

    @Autowired
    CourseClassRepository courseClassRepository;

    @Autowired
    CourseGroupRepository courseGroupRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    CourseClassRequestMapper requestMapper;

    @Autowired
    CourseClassResponseMapper responseMapper;

    @AfterEach
    void tearDown() {
        courseClassRepository.deleteAll();
        courseGroupRepository.deleteAll();
        teacherRepository.deleteAll();
        buildingRepository.deleteAll();
        courseRepository.deleteAll();
        subjectRepository.deleteAll();
    }

    @Test
    void createCourseClassAsAdminShouldReturnCreated() throws Exception {
        CourseClass courseClass = courseClass();
        CourseClassRequestDto requestDto = requestMapper.toDto(courseClass);
        CourseClassResponseDto expected = responseMapper.toDto(courseClass);
        ResultActions result = mvc.perform(post("/course-class")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isOk());
        assertCourseClass(result, expected);
        assertEquals(1, courseClassRepository.count());
    }

    @Test
    void createCourseClassAsStudentShouldBeForbidden() throws Exception {
        CourseClassRequestDto requestDto = requestMapper.toDto(courseClass());
        mvc.perform(post("/course-class")
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andExpect(status().isForbidden());
        assertEquals(0, courseClassRepository.count());
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getAllCourseClassesShouldReturnList(String token) throws Exception {
        CourseClass saved = courseClassRepository.save(courseClass());
        CourseClassResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/course-class")
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertCourseClassArray(result, 0, expected);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdShouldReturnCourseClass(String token) throws Exception {
        CourseClass saved = courseClassRepository.save(courseClass());
        CourseClassResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(get("/course-class/{id}", saved.getId())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        assertCourseClass(result, expected);
    }

    @MethodSource("authTokens")
    @ParameterizedTest(name = "As {0}")
    void getByIdNonExistentShouldReturnBadRequest(String token) throws Exception {
        mvc.perform(get("/course-class/{id}", UUID.randomUUID())
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void updateCourseClassAsAdminShouldUpdate() throws Exception {
        CourseClass saved = courseClassRepository.save(courseClass());
        saved.setTitle("Updated");
        CourseClassRequestDto updateDto = requestMapper.toDto(saved);
        CourseClassResponseDto expected = responseMapper.toDto(saved);
        ResultActions result = mvc.perform(patch("/course-class/{id}", saved.getId())
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateDto))
        ).andExpect(status().isOk());
        assertCourseClass(result, expected);
    }

    @Test
    void updateCourseClassAsStudentShouldBeForbidden() throws Exception {
        CourseClass saved = courseClassRepository.save(courseClass());
        CourseClassRequestDto updateDto = requestMapper.toDto(saved);
        mvc.perform(patch("/course-class/{id}", saved.getId())
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateDto))
        ).andExpect(status().isForbidden());
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

    private Teacher teacher() {
        return teacherRepository.save(Teacher.builder()
                .firstName("Name")
                .lastName("Last name")
                .patronymic("Patronymic")
                .email("test@example.com")
                .build());
    }

    private Building building() {
        return buildingRepository.save(Building.builder()
                .name("Name")
                .address("Address")
                .latitude(BigDecimal.valueOf(50.4501))
                .longitude(BigDecimal.valueOf(30.5234))
                .build());
    }

    private CourseGroup group() {
        return courseGroupRepository.save(CourseGroup.builder()
                .course(course())
                .groupNumber(1)
                .maxStudents(30)
                .numEnrolled(0)
                .build());
    }

    private CourseClass courseClass() {
        return CourseClass.builder()
                .group(group())
                .title("Title")
                .type(ClassType.LECTURE)
                .weeksList("1,2,3")
                .weekDay(WeekDay.MONDAY)
                .classNumber(2)
                .location("203")
                .building(building())
                .teacher(teacher())
                .build();
    }

}