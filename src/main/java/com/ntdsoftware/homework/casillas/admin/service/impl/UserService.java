package com.ntdsoftware.homework.casillas.admin.service.impl;

import static com.ntdsoftware.homework.casillas.common.ValidationUtils.validate;

import com.ntdsoftware.homework.casillas.admin.controller.request.QueryUserRequest;
import com.ntdsoftware.homework.casillas.admin.controller.request.UserRequest;
import com.ntdsoftware.homework.casillas.admin.controller.response.UserResponse;
import com.ntdsoftware.homework.casillas.admin.exception.RoleNotFoundException;
import com.ntdsoftware.homework.casillas.admin.exception.UniqueAdminUserException;
import com.ntdsoftware.homework.casillas.admin.exception.UserAlreadyExistsException;
import com.ntdsoftware.homework.casillas.admin.exception.UserNotFoundException;
import com.ntdsoftware.homework.casillas.admin.service.IUserService;
import com.ntdsoftware.homework.casillas.common.controller.request.DataQueryRequest;
import com.ntdsoftware.homework.casillas.common.entity.Role;
import com.ntdsoftware.homework.casillas.common.entity.User;
import com.ntdsoftware.homework.casillas.common.entity.specificationBuilder.UserSpecificationBuilder;
import com.ntdsoftware.homework.casillas.common.enums.RolesEnum;
import com.ntdsoftware.homework.casillas.common.enums.StatusEnum;
import com.ntdsoftware.homework.casillas.common.exception.NoResultException;
import com.ntdsoftware.homework.casillas.common.repository.RoleRepository;
import com.ntdsoftware.homework.casillas.common.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * UserService provides the implementation of the IUserService interface.
 * It handles the business logic for user management, including creating, retrieving,
 * updating, and deleting users, as well as retrieving all users with pagination and sorting.
 * This service interacts with the UserRepository and RoleRepository to perform database operations.
 * It also uses BCryptPasswordEncoder for password encryption.
 * The service includes validation to ensure that there is always at least one active admin user.
 *
 * @see IUserService
 * @see UserRepository
 * @see RoleRepository
 * @see BCryptPasswordEncoder
 */
@Service
@Slf4j
public class UserService implements IUserService {

    /** User repository */
    private final UserRepository userRepository;

    /** Role repository */
    private final RoleRepository roleRepository;

    /** Password encoder */
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Constructor
     * @param userRepository - User repository
     * @param roleRepository - Role repository
     * @param passwordEncoder - Password encoder
     */
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {

        Role role = getRole(userRequest.getRole());

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

        return user.mapToResponse();
    }

    @Override
    public UserResponse getUser(int userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        log.info("User {} retrieved: {}", user.getId(), user.getUsername());

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .active(user.getStatus())
                .role(user.getRole().getRole())
                .createdDate(user.getCreatedDate())
                .build();
    }

    @Override
    public UserResponse updateUser(int userId, UserRequest userRequest) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (validate().isNullOrEmpty(userRequest.getUsername())) {
            log.error("Username is required");
            throw new UserNotFoundException();
        }

        if (!user.getUsername().equalsIgnoreCase(userRequest.getUsername())) {
            log.error("User {} not found", userRequest.getUsername());
            throw new UserNotFoundException(userRequest.getUsername());
        }

        if (null != userRequest.getRole()) {
            Role roleRequest = getRole(userRequest.getRole());

            // Validate change of admin role
            if (user.getRole().getRole().equals(RolesEnum.ADMIN)
                    && !roleRequest.getRole().equals(RolesEnum.ADMIN)) {

                validateUniqueAdminRole();
            }

            user.setRole(roleRequest);
        }

        if (null != userRequest.getPassword()) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        if (null != userRequest.getActive()) {

            // Validate admin user will be inactive
            if ((user.getRole().getRole().equals(RolesEnum.ADMIN)
                    && user.getStatus().equals(StatusEnum.ACTIVE))
                    && !userRequest.getActive()) {

                validateUniqueAdminRole();
            }

            user.setStatus(userRequest.getActive() ? StatusEnum.ACTIVE : StatusEnum.INACTIVE);
        }

        user = userRepository.save(user);

        log.info("User {} updated: {}", user.getId(), user.getUsername());

        return user.mapToResponse();
    }

    @Override
    public void deleteUser(int userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (user.getRole().getRole().equals(RolesEnum.ADMIN)) {
            validateUniqueAdminRole();
        }

        userRepository.delete(user);
        log.info("User {} has been deleted", user.getUsername());
    }

    @Override
    public Page<UserResponse> getAllUsers(DataQueryRequest<QueryUserRequest> userRequest) {

        UserSpecificationBuilder specificationBuilder = new UserSpecificationBuilder()
                .setUsername(userRequest.getQuery().getUsername())
                .setCreatedDateFrom(userRequest.getQuery().getCreatedDateFrom())
                .setCreatedDateTo(userRequest.getQuery().getCreatedDateTo())
                .setDeleted(userRequest.getQuery().getDeleted());

        if (null != userRequest.getQuery().getStatus()) {
            specificationBuilder.setStatus(StatusEnum.getStatus(userRequest.getQuery().getStatus()));
        }

        if (null != userRequest.getQuery().getRole()) {
            specificationBuilder.setRole(getRole(userRequest.getQuery().getRole()));
        }

        Pageable pageable = PageRequest.of(userRequest.getPage(), userRequest.getSize(), Sort.by(userRequest.getSorts()));
        Specification<User> specification = specificationBuilder.buildSpecification();
        Page<User> users = null;

        users = (null != specification) ? userRepository.findAll(specification, pageable) : userRepository.findAll(pageable);

        if (users.getNumberOfElements() < 1) {
            log.error("No users found");
            throw new NoResultException();
        }

        log.info("Users retrieved: {} in total page(s): {}", users.getTotalElements(), users.getTotalPages());
        return users.map(User::mapToResponse);
    }

    /**
     * Get role
     * @param role - Role
     * @return Role
     * @throws RoleNotFoundException - Role not found exception
     */
    private Role getRole(String role) throws RoleNotFoundException {

        RolesEnum roleEnum = RolesEnum.getRole(role.toUpperCase());

        return roleRepository.findByRole(roleEnum)
                .orElseThrow(() -> new RoleNotFoundException(role));
    }

    /**
     * Validate that must exists at least one admin role
     * @throws UniqueAdminUserException - Unique admin user exception
     */
    private void validateUniqueAdminRole() throws UniqueAdminUserException {
        if (userRepository.countByStatusAndRole_role(StatusEnum.ACTIVE, RolesEnum.ADMIN) <= 1) {
            log.error("There must be at least one admin user");
            throw new UniqueAdminUserException();
        }
    }

}
