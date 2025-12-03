package ua.unsober.backend.feature.building;

import org.springframework.data.jpa.domain.Specification;

public class BuildingSpecification {

    private BuildingSpecification() {}

    public static Specification<Building> hasNameContaining(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isBlank())
                return null;
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + name.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Building> buildSpecification(BuildingFilterDto filters) {
        return Specification.allOf(
                hasNameContaining(filters.getName())
        );
    }
}
