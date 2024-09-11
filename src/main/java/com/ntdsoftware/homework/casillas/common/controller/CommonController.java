package com.ntdsoftware.homework.casillas.common.controller;

import com.ntdsoftware.homework.casillas.admin.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;

public class CommonController {

    protected final IUserService userService;

    public CommonController(IUserService userService) {
        this.userService = userService;
    }

    protected int getUserId(HttpServletRequest request) {
        String userName = (String) request.getAttribute("userName");
        return userService.getUserId(userName);
    }

}
