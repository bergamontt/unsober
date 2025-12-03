package ua.unsober.backend.feature.faculty;

import org.springframework.data.jpa.domain.Specification;

public class FacultySpecification {

    private FacultySpecification() {}

    public static Specification<Faculty> hasNameContaining(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isBlank())
                return null;
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + name.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Faculty> buildSpecification(FacultyFilterDto filters) {
        return Specification.allOf(
                hasNameContaining(filters.getName())
        );
    }
}
