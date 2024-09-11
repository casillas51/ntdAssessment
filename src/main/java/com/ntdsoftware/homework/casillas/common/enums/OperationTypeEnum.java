package com.ntdsoftware.homework.casillas.common.enums;

import com.ntdsoftware.homework.casillas.common.exception.OperationTypeNotFoundException;

public enum OperationTypeEnum {

    ADDITION,

    SUBTRACTION,

    MULTIPLICATION,

    DIVISION,

    SQUARE_ROOT,

    RANDOM_STRING;

    public static OperationTypeEnum getOperationType(String operationType) {

        for (OperationTypeEnum operation : OperationTypeEnum.values()) {
            if (operation.name().equals(operationType.toUpperCase())) {
                return operation;
            }
        }

        throw new OperationTypeNotFoundException(operationType);
    }
}
