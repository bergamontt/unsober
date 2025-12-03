package ua.unsober.backend.feature.speciality;

import org.springframework.data.jpa.domain.Specification;

public class SpecialitySpecification {

    private SpecialitySpecification() {}

    public static Specification<Speciality> hasNameContaining(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isBlank())
                return null;
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + name.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Speciality> buildSpecification(SpecialityFilterDto filters) {
        return Specification.allOf(
                hasNameContaining(filters.getName())
        );
    }
}
