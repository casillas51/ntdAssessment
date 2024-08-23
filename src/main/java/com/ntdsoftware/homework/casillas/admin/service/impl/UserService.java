package com.ntdsoftware.homework.casillas.admin.service.impl;

import com.ntdsoftware.homework.casillas.admin.controller.request.UserRequest;
import com.ntdsoftware.homework.casillas.admin.controller.response.UserResponse;
import com.ntdsoftware.homework.casillas.admin.service.IUserService;
import com.ntdsoftware.homework.casillas.common.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        return null;
    }

    @Override
    public UserResponse getUser(UserRequest userRequest) {
        return null;
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest) {
        return null;
    }

    @Override
    public void deleteUser(UserRequest userRequest) {

    }

    @Override
    public List<UserResponse> getAllUsers() {
        return List.of();
    }
}
