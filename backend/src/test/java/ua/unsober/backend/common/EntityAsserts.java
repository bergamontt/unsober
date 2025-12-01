package ua.unsober.backend.common;

import org.springframework.test.web.servlet.ResultActions;
import ua.unsober.backend.feature.admin.AdminResponseDto;
import ua.unsober.backend.feature.building.BuildingResponseDto;
import ua.unsober.backend.feature.department.DepartmentResponseDto;
import ua.unsober.backend.feature.faculty.FacultyResponseDto;
import ua.unsober.backend.feature.speciality.SpecialityResponseDto;
import ua.unsober.backend.feature.student.StudentResponseDto;
import ua.unsober.backend.feature.subject.SubjectResponseDto;
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

}
