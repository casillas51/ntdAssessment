package com.ntdsoftware.homework.casillas.common.controller.response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BasicResponse {

    private Timestamp timestamp;

    private String status;

    private String message;

    public BasicResponse(String status, String message) {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.status = status;
        this.message = message;
    }

}
