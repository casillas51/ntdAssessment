package com.ntdsoftware.homework.casillas.common.enums;

import lombok.Getter;
import org.springframework.data.domain.Sort;

/**
 * SortsEnum defines the sorting order for queries.
 * It provides two sorting options: ascending (ASC) and descending (DESC).
 */
@Getter
public enum SortsEnum {

    /** Ascending order */
    ASC(Sort.Direction.ASC),

    /** Descending order */
    DESC(Sort.Direction.DESC);

    /** The direction of the sorting order */
    private final Sort.Direction direction;

    /**
     * Constructs a new SortsEnum with the specified sorting direction.
     *
     * @param direction the direction of the sorting order
     */
    private SortsEnum(Sort.Direction direction) {
        this.direction = direction;
    }

    /**
     * Returns the sorting order based on the provided sort string.
     * If the provided sort string does not match any sorting order, the default is ascending (ASC).
     *
     * @param sort the sort string to be converted to a SortsEnum
     * @return the corresponding SortsEnum value, or ASC if no match is found
     */
    public static SortsEnum getSort(String sort) {

        for(SortsEnum s : SortsEnum.values()) {
            if(s.name().equals(sort.toUpperCase())) {
                return s;
            }
        }

        return ASC;
    }
}
