package ua.unsober.backend.common;

import org.springframework.test.web.servlet.ResultActions;
import ua.unsober.backend.feature.admin.AdminResponseDto;
import ua.unsober.backend.feature.building.BuildingResponseDto;
import ua.unsober.backend.feature.course.CourseResponseDto;
import ua.unsober.backend.feature.courseclass.CourseClassResponseDto;
import ua.unsober.backend.feature.coursegroup.CourseGroupResponseDto;
import ua.unsober.backend.feature.department.DepartmentResponseDto;
import ua.unsober.backend.feature.faculty.FacultyResponseDto;
import ua.unsober.backend.feature.request.enrollment.EnrollmentRequestResponseDto;
import ua.unsober.backend.feature.request.withdrawal.WithdrawalRequestResponseDto;
import ua.unsober.backend.feature.speciality.SpecialityResponseDto;
import ua.unsober.backend.feature.student.StudentResponseDto;
import ua.unsober.backend.feature.studentenrollment.StudentEnrollmentResponseDto;
import ua.unsober.backend.feature.subject.SubjectResponseDto;
import ua.unsober.backend.feature.subjectrecommendation.SubjectRecommendationResponseDto;
import ua.unsober.backend.feature.teacher.TeacherResponseDto;
import ua.unsober.backend.feature.terminfo.TermInfoResponseDto;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EntityAsserts {

    public static void assertTermInfo(ResultActions result, TermInfoResponseDto dto) throws Exception {
        result.andExpect(jsonPath("$.studyYear").value(dto.getStudyYear()))
                .andExpect(jsonPath("$.term").value(dto.getTerm().name()))
                .andExpect(jsonPath("$.startDate").value(dto.getStartDate().toString()))
                .andExpect(jsonPath("$.endDate").value(dto.getEndDate().toString()))
                .andExpect(jsonPath("$.lenWeeks").value(dto.getLenWeeks()));
    }

    public static void assertTermInfoArray(ResultActions result, int index, TermInfoResponseDto dto) throws Exception {
        result.andExpect(jsonPath("$[" + index + "].studyYear").value(dto.getStudyYear()))
                .andExpect(jsonPath("$[" + index + "].term").value(dto.getTerm().name()))
                .andExpect(jsonPath("$[" + index + "].startDate").value(dto.getStartDate().toString()))
                .andExpect(jsonPath("$[" + index + "].endDate").value(dto.getEndDate().toString()))
                .andExpect(jsonPath("$[" + index + "].lenWeeks").value(dto.getLenWeeks()));
    }

    public static void assertTeacher(ResultActions result, TeacherResponseDto dto) throws Exception {
        result.andExpect(jsonPath("$.firstName").value(dto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(dto.getLastName()))
                .andExpect(jsonPath("$.patronymic").value(dto.getPatronymic()))
                .andExpect(jsonPath("$.email").value(dto.getEmail()));
    }

    public static void assertTeacherArray(ResultActions result, int index, TeacherResponseDto dto) throws Exception {
        result.andExpect(jsonPath("$[" + index + "].firstName").value(dto.getFirstName()))
                .andExpect(jsonPath("$[" + index + "].lastName").value(dto.getLastName()))
                .andExpect(jsonPath("$[" + index + "].patronymic").value(dto.getPatronymic()))
                .andExpect(jsonPath("$[" + index + "].email").value(dto.getEmail()));
    }

    public static void assertSubject(ResultActions result, SubjectResponseDto dto) throws Exception {
        result.andExpect(jsonPath("$.name").value(dto.getName()))
                .andExpect(jsonPath("$.annotation").value(dto.getAnnotation()))
                .andExpect(jsonPath("$.facultyName").value(dto.getFacultyName()))
                .andExpect(jsonPath("$.departmentName").value(dto.getDepartmentName()))
                .andExpect(jsonPath("$.educationLevel").value(dto.getEducationLevel().name()))
                .andExpect(jsonPath("$.credits").value(dto.getCredits().doubleValue()))
                .andExpect(jsonPath("$.hoursPerWeek").value(dto.getHoursPerWeek()))
                .andExpect(jsonPath("$.term").value(dto.getTerm().name()));
    }

    public static void assertSubjectArray(ResultActions result, int index, SubjectResponseDto dto) throws Exception {
        String prefix = "$.content[" + index + "]";
        result.andExpect(jsonPath(prefix + ".name").value(dto.getName()))
                .andExpect(jsonPath(prefix + ".annotation").value(dto.getAnnotation()))
                .andExpect(jsonPath(prefix + ".facultyName").value(dto.getFacultyName()))
                .andExpect(jsonPath(prefix + ".departmentName").value(dto.getDepartmentName()))
                .andExpect(jsonPath(prefix + ".educationLevel").value(dto.getEducationLevel().name()))
                .andExpect(jsonPath(prefix + ".credits").value(dto.getCredits().doubleValue()))
                .andExpect(jsonPath(prefix + ".hoursPerWeek").value(dto.getHoursPerWeek()))
                .andExpect(jsonPath(prefix + ".term").value(dto.getTerm().name()));
    }

    public static void assertBuilding(ResultActions result, BuildingResponseDto dto) throws Exception {
        result.andExpect(jsonPath("$.name").value(dto.getName()))
                .andExpect(jsonPath("$.address").value(dto.getAddress()))
                .andExpect(jsonPath("$.latitude").value(dto.getLatitude()))
                .andExpect(jsonPath("$.longitude").value(dto.getLongitude()));
    }

    public static void assertBuildingArray(ResultActions result, int index, BuildingResponseDto dto) throws Exception {
        result.andExpect(jsonPath("$[" + index + "].name").value(dto.getName()))
                .andExpect(jsonPath("$[" + index + "].address").value(dto.getAddress()))
                .andExpect(jsonPath("$[" + index + "].latitude").value(dto.getLatitude()))
                .andExpect(jsonPath("$[" + index + "].longitude").value(dto.getLongitude()));
    }

    public static void assertAdmin(ResultActions result, AdminResponseDto expected) throws Exception {
        result.andExpect(jsonPath("$.firstName").value(expected.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(expected.getLastName()))
                .andExpect(jsonPath("$.patronymic").value(expected.getPatronymic()))
                .andExpect(jsonPath("$.email").value(expected.getEmail()));
    }

    public static void assertAuthResponse(ResultActions result) throws Exception {
        result.andExpect(jsonPath("$.token", notNullValue()))
                .andExpect(jsonPath("$.token", not(emptyString())))
                .andExpect(jsonPath("$.expiresAt", notNullValue()))
                .andExpect(jsonPath("$.expiresAt", greaterThan(System.currentTimeMillis())));
    }

    public static void assertDepartment(ResultActions result, DepartmentResponseDto expected) throws Exception {
        result.andExpect(jsonPath("$.faculty.id").value(expected.getFaculty().getId().toString()))
                .andExpect(jsonPath("$.faculty.name").value(expected.getFaculty().getName()))
                .andExpect(jsonPath("$.faculty.description").value(expected.getFaculty().getDescription()))
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.description").value(expected.getDescription()));
    }

    public static void assertDepartmentArray(ResultActions result, int index, DepartmentResponseDto expected) throws Exception {
        result.andExpect(jsonPath("$[" + index + "].faculty.id").value(expected.getFaculty().getId().toString()))
                .andExpect(jsonPath("$[" + index + "].faculty.name").value(expected.getFaculty().getName()))
                .andExpect(jsonPath("$[" + index + "].faculty.description").value(expected.getFaculty().getDescription()))
                .andExpect(jsonPath("$[" + index + "].name").value(expected.getName()))
                .andExpect(jsonPath("$[" + index + "].description").value(expected.getDescription()));
    }

    public static void assertFaculty(ResultActions result, FacultyResponseDto expected) throws Exception {
        result.andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.description").value(expected.getDescription()));
    }

    public static void assertFacultyArray(ResultActions result, int index, FacultyResponseDto expected) throws Exception {
        result.andExpect(jsonPath("$[" + index + "].name").value(expected.getName()))
                .andExpect(jsonPath("$[" + index + "].description").value(expected.getDescription()));
    }

    public static void assertSpeciality(ResultActions result, SpecialityResponseDto expected) throws Exception {
        result.andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.description").value(expected.getDescription()))
                .andExpect(jsonPath("$.department.id").value(expected.getDepartment().getId().toString()))
                .andExpect(jsonPath("$.department.name").value(expected.getDepartment().getName()));
    }

    public static void assertSpecialityArray(ResultActions result, int index, SpecialityResponseDto expected) throws Exception {
        result.andExpect(jsonPath("$[" + index + "].name").value(expected.getName()))
                .andExpect(jsonPath("$[" + index + "].description").value(expected.getDescription()))
                .andExpect(jsonPath("$[" + index + "].department.id").value(expected.getDepartment().getId().toString()))
                .andExpect(jsonPath("$[" + index + "].department.name").value(expected.getDepartment().getName()));
    }

    public static void assertStudent(ResultActions result, StudentResponseDto expected) throws Exception {
        result.andExpect(jsonPath("$.firstName").value(expected.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(expected.getLastName()))
                .andExpect(jsonPath("$.patronymic").value(expected.getPatronymic()))
                .andExpect(jsonPath("$.recordBookNumber").value(expected.getRecordBookNumber()))
                .andExpect(jsonPath("$.email").value(expected.getEmail()))
                .andExpect(jsonPath("$.studyYear").value(expected.getStudyYear()))
                .andExpect(jsonPath("$.status").value(expected.getStatus().name()))
                .andExpect(jsonPath("$.speciality.id").value(expected.getSpeciality().getId().toString()));
    }

    public static void assertStudentArray(ResultActions result, int index, StudentResponseDto expected) throws Exception {
        result.andExpect(jsonPath("$[" + index + "].firstName").value(expected.getFirstName()))
                .andExpect(jsonPath("$[" + index + "].lastName").value(expected.getLastName()))
                .andExpect(jsonPath("$[" + index + "].patronymic").value(expected.getPatronymic()))
                .andExpect(jsonPath("$[" + index + "].recordBookNumber").value(expected.getRecordBookNumber()))
                .andExpect(jsonPath("$[" + index + "].email").value(expected.getEmail()))
                .andExpect(jsonPath("$[" + index + "].studyYear").value(expected.getStudyYear()))
                .andExpect(jsonPath("$[" + index + "].status").value(expected.getStatus().name()))
                .andExpect(jsonPath("$[" + index + "].speciality.id").value(expected.getSpeciality().getId().toString()));
    }

    public static void assertSubjectRecommendation(ResultActions result, SubjectRecommendationResponseDto expected) throws Exception {
        result.andExpect(jsonPath("$.subject.id").value(expected.getSubject().getId().toString()))
                .andExpect(jsonPath("$.subject.name").value(expected.getSubject().getName()))
                .andExpect(jsonPath("$.speciality.id").value(expected.getSpeciality().getId().toString()))
                .andExpect(jsonPath("$.speciality.name").value(expected.getSpeciality().getName()))
                .andExpect(jsonPath("$.recommendation").value(expected.getRecommendation().name()));
    }

    public static void assertSubjectRecommendationArray(ResultActions result, int index, SubjectRecommendationResponseDto expected) throws Exception {
        result.andExpect(jsonPath("$[" + index + "].subject.id").value(expected.getSubject().getId().toString()))
                .andExpect(jsonPath("$[" + index + "].subject.name").value(expected.getSubject().getName()))
                .andExpect(jsonPath("$[" + index + "].speciality.id").value(expected.getSpeciality().getId().toString()))
                .andExpect(jsonPath("$[" + index + "].speciality.name").value(expected.getSpeciality().getName()))
                .andExpect(jsonPath("$[" + index + "].recommendation").value(expected.getRecommendation().name()));
    }

    public static void assertCourse(ResultActions result, CourseResponseDto expected) throws Exception {
        result.andExpect(jsonPath("$.subject.name").value(expected.getSubject().getName()))
                .andExpect(jsonPath("$.maxStudents").value(expected.getMaxStudents()))
                .andExpect(jsonPath("$.numEnrolled").value(expected.getNumEnrolled()))
                .andExpect(jsonPath("$.courseYear").value(expected.getCourseYear()));
    }

    public static void assertCourseArray(ResultActions result, int index, CourseResponseDto expected) throws Exception {
        String prefix = "$.content[" + index + "]";
        result.andExpect(jsonPath(prefix + ".subject.name").value(expected.getSubject().getName()))
                .andExpect(jsonPath(prefix + ".maxStudents").value(expected.getMaxStudents()))
                .andExpect(jsonPath(prefix + ".numEnrolled").value(expected.getNumEnrolled()))
                .andExpect(jsonPath(prefix + ".courseYear").value(expected.getCourseYear()));
    }

    public static void assertCourseGroup(ResultActions result, CourseGroupResponseDto expected) throws Exception {
        result.andExpect(jsonPath("$.course.subject.name").value(expected.getCourse().getSubject().getName()))
                .andExpect(jsonPath("$.groupNumber").value(expected.getGroupNumber()))
                .andExpect(jsonPath("$.maxStudents").value(expected.getMaxStudents()))
                .andExpect(jsonPath("$.numEnrolled").value(expected.getNumEnrolled()))
                .andExpect(jsonPath("$.course.courseYear").value(expected.getCourse().getCourseYear()));
    }

    public static void assertCourseGroupArray(ResultActions result, int index, CourseGroupResponseDto expected) throws Exception {
        String prefix = "$[" + index + "]";
        result.andExpect(jsonPath(prefix + ".course.subject.name").value(expected.getCourse().getSubject().getName()))
                .andExpect(jsonPath(prefix + ".groupNumber").value(expected.getGroupNumber()))
                .andExpect(jsonPath(prefix + ".maxStudents").value(expected.getMaxStudents()))
                .andExpect(jsonPath(prefix + ".numEnrolled").value(expected.getNumEnrolled()))
                .andExpect(jsonPath(prefix + ".course.courseYear").value(expected.getCourse().getCourseYear()));
    }

    public static void assertCourseClass(ResultActions result, CourseClassResponseDto expected) throws Exception {
        result.andExpect(jsonPath("$.group.id").value(expected.getGroup().getId().toString()))
                .andExpect(jsonPath("$.group.groupNumber").value(expected.getGroup().getGroupNumber()))
                .andExpect(jsonPath("$.group.maxStudents").value(expected.getGroup().getMaxStudents()))
                .andExpect(jsonPath("$.group.numEnrolled").value(expected.getGroup().getNumEnrolled()))
                .andExpect(jsonPath("$.group.course.subject.name").value(expected.getGroup().getCourse().getSubject().getName()))
                .andExpect(jsonPath("$.group.course.courseYear").value(expected.getGroup().getCourse().getCourseYear()))
                .andExpect(jsonPath("$.title").value(expected.getTitle()))
                .andExpect(jsonPath("$.type").value(expected.getType().name()))
                .andExpect(jsonPath("$.weeksList", equalTo(expected.getWeeksList())))
                .andExpect(jsonPath("$.weekDay").value(expected.getWeekDay().name()))
                .andExpect(jsonPath("$.classNumber").value(expected.getClassNumber()))
                .andExpect(jsonPath("$.location").value(expected.getLocation()))
                .andExpect(jsonPath("$.building.id").value(expected.getBuilding() != null ? expected.getBuilding().getId().toString() : null))
                .andExpect(jsonPath("$.teacher.id").value(expected.getTeacher() != null ? expected.getTeacher().getId().toString() : null));
    }

    public static void assertCourseClassArray(ResultActions result, int index, CourseClassResponseDto expected) throws Exception {
        String prefix = "$[" + index + "]";
        result.andExpect(jsonPath(prefix + ".group.id").value(expected.getGroup().getId().toString()))
                .andExpect(jsonPath(prefix + ".group.groupNumber").value(expected.getGroup().getGroupNumber()))
                .andExpect(jsonPath(prefix + ".group.maxStudents").value(expected.getGroup().getMaxStudents()))
                .andExpect(jsonPath(prefix + ".group.numEnrolled").value(expected.getGroup().getNumEnrolled()))
                .andExpect(jsonPath(prefix + ".group.course.subject.name").value(expected.getGroup().getCourse().getSubject().getName()))
                .andExpect(jsonPath(prefix + ".group.course.courseYear").value(expected.getGroup().getCourse().getCourseYear()))
                .andExpect(jsonPath(prefix + ".title").value(expected.getTitle()))
                .andExpect(jsonPath(prefix + ".type").value(expected.getType().name()))
                .andExpect(jsonPath(prefix + ".weeksList", equalTo(expected.getWeeksList())))
                .andExpect(jsonPath(prefix + ".classNumber").value(expected.getClassNumber()))
                .andExpect(jsonPath(prefix + ".location").value(expected.getLocation()))
                .andExpect(jsonPath(prefix + ".building.id").value(expected.getBuilding() != null ? expected.getBuilding().getId().toString() : null))
                .andExpect(jsonPath(prefix + ".teacher.id").value(expected.getTeacher() != null ? expected.getTeacher().getId().toString() : null));
    }

    public static void assertStudentEnrollment(ResultActions result, StudentEnrollmentResponseDto expected) throws Exception {
        result.andExpect(jsonPath("$.student.id").value(expected.getStudent().getId().toString()))
                .andExpect(jsonPath("$.student.firstName").value(expected.getStudent().getFirstName()))
                .andExpect(jsonPath("$.student.lastName").value(expected.getStudent().getLastName()))
                .andExpect(jsonPath("$.student.patronymic").value(expected.getStudent().getPatronymic()))
                .andExpect(jsonPath("$.student.email").value(expected.getStudent().getEmail()))
                .andExpect(jsonPath("$.student.recordBookNumber").value(expected.getStudent().getRecordBookNumber()))
                .andExpect(jsonPath("$.student.studyYear").value(expected.getStudent().getStudyYear()))
                .andExpect(jsonPath("$.student.status").value(expected.getStudent().getStatus().name()))
                .andExpect(jsonPath("$.course.id").value(expected.getCourse().getId().toString()))
                .andExpect(jsonPath("$.course.subject.name").value(expected.getCourse().getSubject().getName()))
                .andExpect(jsonPath("$.course.courseYear").value(expected.getCourse().getCourseYear()))
                .andExpect(jsonPath("$.course.maxStudents").value(expected.getCourse().getMaxStudents()))
                .andExpect(jsonPath("$.course.numEnrolled").value(expected.getCourse().getNumEnrolled()))
                .andExpect(jsonPath("$.status").value(expected.getStatus().name()))
                .andExpect(jsonPath("$.enrollmentYear").value(expected.getEnrollmentYear()))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    public static void assertStudentEnrollmentArray(ResultActions result, int index, StudentEnrollmentResponseDto expected) throws Exception {
        String prefix = "$[" + index + "]";
        result.andExpect(jsonPath(prefix + ".student.id").value(expected.getStudent().getId().toString()))
                .andExpect(jsonPath(prefix + ".student.firstName").value(expected.getStudent().getFirstName()))
                .andExpect(jsonPath(prefix + ".student.lastName").value(expected.getStudent().getLastName()))
                .andExpect(jsonPath(prefix + ".student.patronymic").value(expected.getStudent().getPatronymic()))
                .andExpect(jsonPath(prefix + ".student.email").value(expected.getStudent().getEmail()))
                .andExpect(jsonPath(prefix + ".student.recordBookNumber").value(expected.getStudent().getRecordBookNumber()))
                .andExpect(jsonPath(prefix + ".student.studyYear").value(expected.getStudent().getStudyYear()))
                .andExpect(jsonPath(prefix + ".student.status").value(expected.getStudent().getStatus().name()))
                .andExpect(jsonPath(prefix + ".course.id").value(expected.getCourse().getId().toString()))
                .andExpect(jsonPath(prefix + ".course.subject.name").value(expected.getCourse().getSubject().getName()))
                .andExpect(jsonPath(prefix + ".course.courseYear").value(expected.getCourse().getCourseYear()))
                .andExpect(jsonPath(prefix + ".status").value(expected.getStatus().name()))
                .andExpect(jsonPath(prefix + ".enrollmentYear").value(expected.getEnrollmentYear()))
                .andExpect(jsonPath(prefix + ".createdAt").exists());
    }

    public static void assertEnrollmentRequest(ResultActions result, EnrollmentRequestResponseDto expected) throws Exception {
        result.andExpect(jsonPath("$.reason").value(expected.getReason()))
                .andExpect(jsonPath("$.status").value(expected.getStatus().toString()))
                .andExpect(jsonPath("$.student.id").value(expected.getStudent().getId().toString()))
                .andExpect(jsonPath("$.course.id").value(expected.getCourse().getId().toString()));
    }

    public static void assertEnrollmentRequestArray(ResultActions result, int index, EnrollmentRequestResponseDto expected) throws Exception {
        result.andExpect(jsonPath("$[" + index + "].reason").value(expected.getReason()))
                .andExpect(jsonPath("$[" + index + "].status").value(expected.getStatus().toString()))
                .andExpect(jsonPath("$[" + index + "].student.id").value(expected.getStudent().getId().toString()))
                .andExpect(jsonPath("$[" + index + "].course.id").value(expected.getCourse().getId().toString()));
    }

    public static void assertWithdrawalRequest(ResultActions result, WithdrawalRequestResponseDto expected) throws Exception {
        result.andExpect(jsonPath("$.reason").value(expected.getReason()))
                .andExpect(jsonPath("$.status").value(expected.getStatus().toString()))
                .andExpect(jsonPath("$.studentEnrollment.id").value(expected.getStudentEnrollment().getId().toString()))
                .andExpect(jsonPath("$.studentEnrollment.student.id").value(expected.getStudentEnrollment().getStudent().getId().toString()))
                .andExpect(jsonPath("$.studentEnrollment.course.id").value(expected.getStudentEnrollment().getCourse().getId().toString()));
    }

    public static void assertWithdrawalRequestArray(ResultActions result, int index, WithdrawalRequestResponseDto expected) throws Exception {
        result.andExpect(jsonPath("$[" + index + "].reason").value(expected.getReason()))
                .andExpect(jsonPath("$[" + index + "].status").value(expected.getStatus().toString()))
                .andExpect(jsonPath("$[" + index + "].studentEnrollment.id").value(expected.getStudentEnrollment().getId().toString()))
                .andExpect(jsonPath("$[" + index + "].studentEnrollment.student.id").value(expected.getStudentEnrollment().getStudent().getId().toString()))
                .andExpect(jsonPath("$[" + index + "].studentEnrollment.course.id").value(expected.getStudentEnrollment().getCourse().getId().toString()));
    }

}