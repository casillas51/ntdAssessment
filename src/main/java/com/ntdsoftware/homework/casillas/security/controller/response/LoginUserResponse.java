package com.ntdsoftware.homework.casillas.security.controller.response;

import com.ntdsoftware.homework.casillas.security.enums.RolesEnum;
import com.ntdsoftware.homework.casillas.security.enums.StatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Getter
@ToString(includeFieldNames = true)
public class LoginUserResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String token;

    private long expiresIn;

    private StatusEnum status;

    private RolesEnum role;

}
