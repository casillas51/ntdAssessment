package com.ntdsoftware.homework.casillas.common.entity.specificationBuilder;

import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;

/**
 * SpecificationRules defines the rules for building specifications for querying entities.
 * It provides a method to apply the rules based on the key and value provided.
 *
 * @param <T> the entity type
 */
public interface SpecificationRules<T> {

    /**
     * Applies the rules to create a specification based on the key and value provided.
     *
     * @param key the key representing the field to be queried
     * @param value the value to be used in the query
     * @return the specification for querying entities
     */
    default Specification<T> applyRules(String key, Object value) {

        if (value instanceof Enum<?> && value.getClass().isEnum()) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(key), ((Enum<?>) value).name());

        } else if (value instanceof String) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(key), "%" + value + "%");

        } else if (value instanceof Timestamp) {

            if (key.contains("From")) {
                return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(key.replace("From", "")), (Timestamp) value);

            } else if (key.contains("To")) {
                return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(key.replace("To", "")), (Timestamp) value);
            }
        }

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(key), value);
    }
}
