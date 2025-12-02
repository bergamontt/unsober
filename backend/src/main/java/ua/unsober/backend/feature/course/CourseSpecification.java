package ua.unsober.backend.feature.course;

import org.springframework.data.jpa.domain.Specification;
import ua.unsober.backend.common.enums.EducationLevel;
import ua.unsober.backend.common.enums.Term;

import java.math.BigDecimal;

public class CourseSpecification {

    private CourseSpecification() {}

    public static Specification<Course> hasSubjectNameContaining(String subjectName) {
        return (root, query, criteriaBuilder) -> {
            if (subjectName == null || subjectName.isBlank())
                return null;
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("subject").get("name")),
                    "%" + subjectName.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Course> hasEducationLevel(EducationLevel educationLevel) {
        return (root, query, criteriaBuilder) -> {
            if (educationLevel == null)
                return null;
            return criteriaBuilder.equal(
                    root.get("subject").get("educationLevel"),
                    educationLevel
            );
        };
    }

    public static Specification<Course> hasCredits(BigDecimal credits) {
        return (root, query, criteriaBuilder) -> {
            if (credits == null)
                return null;
            return criteriaBuilder.equal(
                    root.get("subject").get("credits"),
                    credits
            );
        };
    }

    public static Specification<Course> hasTerm(Term term) {
        return (root, query, criteriaBuilder) -> {
            if (term == null)
                return null;
            return criteriaBuilder.equal(
                    root.get("subject").get("term"),
                    term
            );
        };
    }

    public static Specification<Course> hasCourseYear(Integer courseYear) {
        return (root, query, criteriaBuilder) -> {
            if (courseYear == null)
                return null;
            return criteriaBuilder.equal(
                    root.get("courseYear"),
                    courseYear
            );
        };
    }

    public static Specification<Course> buildSpecification(CourseFilterDto filters) {
        return Specification.allOf(
                hasSubjectNameContaining(filters.getSubjectName()),
                hasEducationLevel(filters.getEducationLevel()),
                hasCredits(filters.getCredits()),
                hasTerm(filters.getTerm()),
                hasCourseYear(filters.getCourseYear())
        );
    }
}