package ua.unsober.backend.feature.department;

import org.springframework.data.jpa.domain.Specification;

public class DepartmentSpecification {

    private DepartmentSpecification() {}

    public static Specification<Department> hasNameContaining(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isBlank())
                return null;
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + name.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Department> buildSpecification(DepartmentFilterDto filters) {
        return Specification.allOf(
                hasNameContaining(filters.getName())
        );
    }
}