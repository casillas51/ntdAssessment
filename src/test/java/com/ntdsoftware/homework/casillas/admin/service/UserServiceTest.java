package com.ntdsoftware.homework.casillas.admin.service;

import com.ntdsoftware.homework.casillas.admin.controller.request.UserRequest;
import com.ntdsoftware.homework.casillas.admin.controller.response.UserResponse;
import com.ntdsoftware.homework.casillas.admin.service.impl.UserService;
import com.ntdsoftware.homework.casillas.common.entity.Role;
import com.ntdsoftware.homework.casillas.common.entity.User;
import com.ntdsoftware.homework.casillas.common.enums.RolesEnum;
import com.ntdsoftware.homework.casillas.common.enums.StatusEnum;
import com.ntdsoftware.homework.casillas.common.exception.ApplicationException;
import com.ntdsoftware.homework.casillas.common.repository.RoleRepository;
import com.ntdsoftware.homework.casillas.common.repository.UserRepository;
import com.ntdsoftware.homework.casillas.configuration.ApplicationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

/**
 * User Service test
 */
public class UserServiceTest implements ApplicationTest {

    /** User repository */
    @Mock
    private UserRepository userRepository;

    /** Role repository */
    @Mock
    private RoleRepository roleRepository;

    /** Password encoder */
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    /** User service */
    private IUserService userService = null;

    /**
     * Setup
     */
    @BeforeEach
    void setup() {
        if (null == userService) {
            userService = new UserService(userRepository, roleRepository, passwordEncoder);
        }
    }

    /**
     * Create a new user
     * @throws ApplicationException - Application exception
     */
    @Test
    void whenCreateUser_thenReturnUserResponse() throws ApplicationException {

        Role role = new Role().setId(1).setRole(RolesEnum.ADMIN);
        User user = new User().setId(100).setUsername("test").setPassword("test").setRole(role).setStatus(StatusEnum.ACTIVE);

        when(roleRepository.findByRole(any(RolesEnum.class))).thenReturn(Optional.of(role));
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(user);

        UserRequest request = new UserRequest().setUsername("test").setPassword("test").setRole("ADMIN").setActive(true);
        UserResponse response = userService.createUser(request);

        assertNotNull(response);
        assertEquals(response.getId(), 100);
        assertEquals(response.getUsername(), "test");
    }

    /**
     * Create a new user with not existing role enum
     */
    @Test
    void whenCreateUserWithNotExistingRoleEnum_thenThrowException() {

        UserRequest request = new UserRequest().setUsername("test").setPassword("test").setRole("NO_EXISTING_ROLE").setActive(true);
        assertThrows(ApplicationException.class, () -> userService.createUser(request));
    }

    /**
     * Create a new user with no existing role in DB
     */
    @Test
    void whenCreateUserWithNoExistingRoleInDB_thenThrowException() {

        when(roleRepository.findByRole(any(RolesEnum.class))).thenReturn(Optional.empty());

        UserRequest request = new UserRequest().setUsername("test").setPassword("test").setRole("USER").setActive(true);
        assertThrows(ApplicationException.class, () -> userService.createUser(request));
    }

    /**
     * Create a new user with invalid username
     */
    @Test
    void whenCreateUserWithExistingUsername_thenThrowException() {

        Role role = new Role().setId(1).setRole(RolesEnum.ADMIN);
        User user = new User().setId(100).setUsername("test").setPassword("test").setRole(role).setStatus(StatusEnum.ACTIVE);

        when(roleRepository.findByRole(any(RolesEnum.class))).thenReturn(Optional.of(role));
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        UserRequest request = new UserRequest().setUsername("test").setPassword("test").setRole("ADMIN").setActive(true);
        assertThrows(ApplicationException.class, () -> userService.createUser(request));
    }

    /**
     * Get a user with existing user id
     * @throws ApplicationException - Application exception
     */
    @Test
    void whenGetUserWithExistingUserId_thenReturnUserResponse() throws ApplicationException {

        Role role = new Role().setId(1).setRole(RolesEnum.ADMIN);
        User user = new User().setId(100).setUsername("test").setPassword("test").setRole(role).setStatus(StatusEnum.ACTIVE);

        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));

        UserResponse response = userService.getUser(100);

        assertNotNull(response);
        assertEquals(response.getId(), 100);
        assertEquals(response.getUsername(), "test");
    }

    /**
     * Get a user with not existing user id
     */
    @Test
    void whenGetUserWithNotExistingUserId_thenThrowException() {

        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        assertThrows(ApplicationException.class, () -> userService.getUser(100));
    }

    /**
     * Update a user with existing user id
     * @throws ApplicationException - Application exception
     */
    @Test
    void whenUpdateUserWithExistingUserId_thenReturnUserResponse() throws ApplicationException {

        Role roleAdmin = new Role().setId(1).setRole(RolesEnum.ADMIN);
        Role roleUser = new Role().setId(2).setRole(RolesEnum.USER);
        User user = new User().setId(100).setUsername("test").setPassword("test").setRole(roleAdmin).setStatus(StatusEnum.ACTIVE);

        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);
        when(roleRepository.findByRole(RolesEnum.ADMIN)).thenReturn(Optional.of(roleAdmin));
        when(roleRepository.findByRole(RolesEnum.USER)).thenReturn(Optional.of(roleUser));
        when(passwordEncoder.encode(anyString())).thenReturn("$2a$10$R0TuFO1A4gDMpaqjxyick.rqTyk6ttPDnkMr9HYU/3iLBomT6ojM6");
        when(userRepository.countByRoleAndStatus(any(RolesEnum.class), any(StatusEnum.class))).thenReturn(4L);

        UserRequest request = new UserRequest().setUsername("test").setPassword("test").setRole("USER").setActive(false);
        UserResponse response = userService.updateUser(100, request);

        assertNotNull(response);
        assertEquals(response.getId(), 100);
        assertEquals(response.getUsername(), "test");
    }

    /**
     * Update a user with not existing user id
     */
    @Test
    void whenUpdateUserWithNotExistingUserId_thenThrowException() {

        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        assertThrows(ApplicationException.class, () -> userService.updateUser(100, new UserRequest()));
    }

    /**
     * Update a user with invalid username
     * @throws ApplicationException - Application exception
     */
    @Test
    void whenUpdateUserWithInvalidUsername_thenThrowException() throws ApplicationException {

        Role role = new Role().setId(1).setRole(RolesEnum.ADMIN);
        User user = new User().setId(100).setUsername("test").setPassword("test").setRole(role).setStatus(StatusEnum.ACTIVE);

        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        assertThrows(ApplicationException.class, () -> userService.updateUser(100, new UserRequest()));
        assertThrows(ApplicationException.class, () -> userService.updateUser(100, new UserRequest().setUsername("")));
        assertThrows(ApplicationException.class, () -> userService.updateUser(100, new UserRequest().setUsername("Admin")));
    }

    /**
     * Update a user with invalid role
     */
    @Test
    void whenUpdateUserWithInvalidRole_thenThrowException() {

        Role role = new Role().setId(1).setRole(RolesEnum.ADMIN);
        User user = new User().setId(100).setUsername("test").setPassword("test").setRole(role).setStatus(StatusEnum.ACTIVE);

        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(roleRepository.findByRole(any(RolesEnum.class))).thenReturn(Optional.empty());
        assertThrows(ApplicationException.class, () -> userService.updateUser(100, new UserRequest().setUsername("test").setRole("INVALID")));
        assertThrows(ApplicationException.class, () -> userService.updateUser(100, new UserRequest().setUsername("test").setRole("ADMIN")));
    }

    /**
     * Update a user with unique admin role
     * @throws ApplicationException - Application exception
     */
    @Test
    void whenUpdateUserWithUniqueAdminRole_thenThrowException() throws ApplicationException {

        Role roleAdmin = new Role().setId(1).setRole(RolesEnum.ADMIN);
        Role roleUser = new Role().setId(2).setRole(RolesEnum.USER);
        User user = new User().setId(100).setUsername("test").setPassword("test").setRole(roleAdmin).setStatus(StatusEnum.ACTIVE);

        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);
        when(roleRepository.findByRole(RolesEnum.ADMIN)).thenReturn(Optional.of(roleAdmin));
        when(roleRepository.findByRole(RolesEnum.USER)).thenReturn(Optional.of(roleUser));
        when(userRepository.countByRoleAndStatus(any(RolesEnum.class), any(StatusEnum.class))).thenReturn(1L);

        assertThrows(ApplicationException.class, () -> userService.updateUser(100, new UserRequest().setUsername("test").setRole("USER")));
        assertThrows(ApplicationException.class, () -> userService.updateUser(100, new UserRequest().setUsername("test").setActive(false)));
    }

    /**
     * Reset mocks
     */
    @AfterEach
    void resetMocks() {
        reset(roleRepository, userRepository, passwordEncoder);
    }
}
