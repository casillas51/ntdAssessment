package com.ntdsoftware.homework.casillas.common.entity.specificationBuilder;

import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;
import java.util.TreeMap;

/**
 * SpecificationBuilder is an interface for building specifications for querying entities.
 * It extends the SpecificationRules interface and provides methods to build the specification map
 * and the specification itself.
 *
 * @param <T> the entity type
 */
public interface SpecificationBuilder<T> extends SpecificationRules<T> {

    /**
     * The specification map that holds the criteria for building the specification.
     */
    TreeMap<String, Object> specificationMap = new TreeMap<>();

    /**
     * Builds the specification map with the provided criteria.
     */
    void build();

    /**
     * Builds the specification based on the criteria in the specification map.
     *
     * @return the specification for querying entities
     */
    default Specification<T> buildSpecification() {

        build();
        specificationMap.values().removeIf(Objects::isNull);
        Specification<T> specification = null;

        for (String key : specificationMap.keySet()) {
            if (specification == null) {
                specification = (root, query, criteriaBuilder) ->
                        applyRules(key, specificationMap.get(key)).toPredicate(root, query, criteriaBuilder);
            } else {
                specification = specification.and((root, query, criteriaBuilder) ->
                        applyRules(key, specificationMap.get(key)).toPredicate(root, query, criteriaBuilder));
            }
        }

        return specification;
    }
}
