package com.ntdsoftware.homework.casillas.admin.service.impl;

import com.ntdsoftware.homework.casillas.admin.controller.request.UserRequest;
import com.ntdsoftware.homework.casillas.admin.controller.response.UserResponse;
import com.ntdsoftware.homework.casillas.admin.exception.RoleNotFoundException;
import com.ntdsoftware.homework.casillas.admin.exception.UserAlreadyExistsException;
import com.ntdsoftware.homework.casillas.admin.service.IUserService;
import com.ntdsoftware.homework.casillas.common.entity.Role;
import com.ntdsoftware.homework.casillas.common.entity.User;
import com.ntdsoftware.homework.casillas.common.enums.RolesEnum;
import com.ntdsoftware.homework.casillas.common.enums.StatusEnum;
import com.ntdsoftware.homework.casillas.common.exception.ApplicationException;
import com.ntdsoftware.homework.casillas.common.repository.RoleRepository;
import com.ntdsoftware.homework.casillas.common.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@Slf4j
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) throws UserAlreadyExistsException, RoleNotFoundException {

        RolesEnum roleEnum = RolesEnum.getRole(userRequest.getRole().toUpperCase());

        Role role = roleRepository.findByRole(roleEnum)
                .orElseThrow(() -> new RoleNotFoundException(userRequest.getRole()));

        User user = userRepository.findByUsername(userRequest.getUsername())
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setUsername(userRequest.getUsername());
                    newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
                    newUser.setStatus(userRequest.getActive() ? StatusEnum.ACTIVE : StatusEnum.INACTIVE);
                    newUser.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                    newUser.setRole(role);
                    return newUser;
                });

        if (user.getId() != 0) {
            throw new UserAlreadyExistsException(userRequest.getUsername());
        }

        user = userRepository.save(user);

        log.info("User {} created: {}", user.getId(), user.getUsername());

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .active(user.getStatus())
                .role(user.getRole().getRole())
                .createdDate(user.getCreatedDate())
                .build();
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
