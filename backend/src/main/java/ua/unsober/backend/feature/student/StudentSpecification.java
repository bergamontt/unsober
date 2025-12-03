package ua.unsober.backend.feature.student;

import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {

    private StudentSpecification() {}

    public static Specification<Student> hasEmailContaining(String email) {
        return (root, query, criteriaBuilder) -> {
            if (email == null || email.isBlank())
                return null;
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("user").get("email")),
                    "%" + email.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Student> buildSpecification(StudentFilterDto filters) {
        return Specification.allOf(
                hasEmailContaining(filters.getEmail())
        );
    }
}
