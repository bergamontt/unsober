package ua.unsober.backend.feature.course;

import lombok.Data;
import ua.unsober.backend.common.enums.EducationLevel;
import ua.unsober.backend.common.enums.Term;

import java.math.BigDecimal;

@Data
public class CourseFilterDto {
    private String subjectName;
    private EducationLevel educationLevel;
    private BigDecimal credits;
    private Term term;
    private Integer courseYear;
}