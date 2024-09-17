package com.ntdsoftware.homework.casillas.common.controller;

import com.ntdsoftware.homework.casillas.admin.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Base REST controller providing common functionality for other controllers.
 * This class provides methods to retrieve user information from the request.
 */
public class CommonRestController {

    /**
     * The IUserService instance to manage users.
     */
    protected final IUserService userService;

    /**
     * Constructs a new CommonRestController with the given IUserService.
     *
     * @param userService the IUserService instance to manage users
     */
    public CommonRestController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves the user ID from the HttpServletRequest.
     * This method extracts the username from the request attributes and uses the IUserService to get the user ID.
     *
     * @param request the HttpServletRequest containing the user information
     * @return the user ID
     */
    protected int getUserId(HttpServletRequest request) {
        String username = String.valueOf(request.getAttribute("userName"));
        return userService.getUserId(username);
    }

}