package ua.unsober.backend.common;

import org.springframework.test.web.servlet.ResultActions;
import ua.unsober.backend.feature.admin.AdminResponseDto;
import ua.unsober.backend.feature.building.BuildingResponseDto;
import ua.unsober.backend.feature.subject.SubjectResponseDto;
import ua.unsober.backend.feature.teacher.TeacherResponseDto;
import ua.unsober.backend.feature.terminfo.TermInfoResponseDto;

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
}
