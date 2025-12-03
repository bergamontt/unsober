package ua.unsober.backend.feature.teacher;

import org.springframework.data.jpa.domain.Specification;

public class TeacherSpecification {

    private TeacherSpecification() {}

    public static Specification<Teacher> hasEmailContaining(String email) {
        return (root, query, criteriaBuilder) -> {
            if (email == null || email.isBlank())
                return null;
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("email")),
                    "%" + email.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Teacher> buildSpecification(TeacherFilterDto filters) {
        return Specification.allOf(
                hasEmailContaining(filters.getEmail())
        );
    }
}
