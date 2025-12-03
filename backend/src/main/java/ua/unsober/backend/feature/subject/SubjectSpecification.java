package ua.unsober.backend.feature.subject;

import org.springframework.data.jpa.domain.Specification;

public class SubjectSpecification {

    private SubjectSpecification() {}

    public static Specification<Subject> hasNameContaining(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isBlank())
                return null;
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + name.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Subject> buildSpecification(SubjectFilterDto filters) {
        return Specification.allOf(
                hasNameContaining(filters.getName())
        );
    }
}
