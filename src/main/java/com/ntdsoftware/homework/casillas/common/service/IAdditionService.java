package com.ntdsoftware.homework.casillas.common.service;

import com.ntdsoftware.homework.casillas.common.controller.response.OperationResultResponse;
import com.ntdsoftware.homework.casillas.common.enums.OperationTypeEnum;

public interface IAdditionService {

    final OperationTypeEnum operationType = OperationTypeEnum.ADDITION;

    OperationResultResponse add(int userId, Double term1, Double term2);

}
