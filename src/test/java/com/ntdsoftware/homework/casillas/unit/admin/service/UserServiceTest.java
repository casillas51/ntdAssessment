package com.ntdsoftware.homework.casillas.unit.admin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntdsoftware.homework.casillas.admin.controller.request.QueryUserRequest;
import com.ntdsoftware.homework.casillas.admin.controller.request.UserRequest;
import com.ntdsoftware.homework.casillas.admin.controller.response.UserResponse;
import com.ntdsoftware.homework.casillas.admin.service.IUserService;
import com.ntdsoftware.homework.casillas.admin.service.impl.UserService;
import com.ntdsoftware.homework.casillas.common.controller.request.DataQueryRequest;
import com.ntdsoftware.homework.casillas.common.entity.Role;
import com.ntdsoftware.homework.casillas.common.entity.User;
import com.ntdsoftware.homework.casillas.common.entity.specificationBuilder.SpecificationBuilder;
import com.ntdsoftware.homework.casillas.common.enums.RolesEnum;
import com.ntdsoftware.homework.casillas.common.enums.SortsEnum;
import com.ntdsoftware.homework.casillas.common.enums.StatusEnum;
import com.ntdsoftware.homework.casillas.common.exception.ApplicationException;
import com.ntdsoftware.homework.casillas.common.repository.RoleRepository;
import com.ntdsoftware.homework.casillas.common.repository.UserRepository;
import com.ntdsoftware.homework.casillas.configuration.ApplicationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * UserServiceTest contains unit tests for the UserService class.
 * It tests various scenarios for creating, retrieving, updating, and deleting users.
 * The tests use Mockito to mock dependencies such as UserRepository, RoleRepository, and BCryptPasswordEncoder.
 * The tests also verify the business logic and exception handling in the UserService class.
 *
 * @see UserService
 * @see UserRepository
 * @see RoleRepository
 * @see BCryptPasswordEncoder
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
        assertUserResponse(userService.createUser(request));

        request = new UserRequest().setUsername("test").setPassword("test").setRole("ADMIN").setActive(false);
        assertUserResponse(userService.createUser(request));
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

        assertUserResponse(userService.getUser(100));
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
    void whenUpdateUserRoleAdminWithExistingUserId_thenReturnUserResponse() throws ApplicationException {

        Role roleAdmin = new Role().setId(1).setRole(RolesEnum.ADMIN);
        Role roleUser = new Role().setId(2).setRole(RolesEnum.USER);
        User user = new User().setId(100).setUsername("test").setPassword("test").setRole(roleAdmin).setStatus(StatusEnum.ACTIVE);

        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);
        when(roleRepository.findByRole(RolesEnum.ADMIN)).thenReturn(Optional.of(roleAdmin));
        when(roleRepository.findByRole(RolesEnum.USER)).thenReturn(Optional.of(roleUser));
        when(passwordEncoder.encode(anyString())).thenReturn("$2a$10$R0TuFO1A4gDMpaqjxyick.rqTyk6ttPDnkMr9HYU/3iLBomT6ojM6");
        when(userRepository.countByStatusAndRole_role(any(StatusEnum.class), any(RolesEnum.class))).thenReturn(4L);

        UserRequest request = new UserRequest().setUsername("test").setPassword("test").setRole("USER").setActive(false);
        assertUserResponse(userService.updateUser(100, request));

        request = new UserRequest().setUsername("test").setPassword("test").setRole("USER").setActive(true);
        assertUserResponse(userService.updateUser(100, request));

        request = new UserRequest().setUsername("test").setPassword("test").setRole("ADMIN").setActive(false);
        assertUserResponse(userService.updateUser(100, request));

        request = new UserRequest().setUsername("test").setPassword("test").setRole("ADMIN").setActive(true);
        assertUserResponse(userService.updateUser(100, request));

        request = new UserRequest().setUsername("test").setPassword("test").setRole("ADMIN");
        assertUserResponse(userService.updateUser(100, request));
    }

    /**
     * Update a user with existing user id
     * @throws ApplicationException - Application exception
     */
    @Test
    void whenUpdateUserRoleUserWithExistingUserId_thenReturnUserResponse() throws ApplicationException {

        Role roleAdmin = new Role().setId(1).setRole(RolesEnum.ADMIN);
        Role roleUser = new Role().setId(2).setRole(RolesEnum.USER);
        User user = new User().setId(100).setUsername("test").setPassword("test").setRole(roleUser).setStatus(StatusEnum.ACTIVE);

        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);
        when(roleRepository.findByRole(RolesEnum.ADMIN)).thenReturn(Optional.of(roleAdmin));
        when(roleRepository.findByRole(RolesEnum.USER)).thenReturn(Optional.of(roleUser));
        when(passwordEncoder.encode(anyString())).thenReturn("$2a$10$R0TuFO1A4gDMpaqjxyick.rqTyk6ttPDnkMr9HYU/3iLBomT6ojM6");
        when(userRepository.countByStatusAndRole_role(any(StatusEnum.class), any(RolesEnum.class))).thenReturn(4L);

        UserRequest request = new UserRequest().setUsername("test").setPassword("test").setRole("USER").setActive(false);
        assertUserResponse(userService.updateUser(100, request));

        request = new UserRequest().setUsername("test").setPassword("test").setRole("USER").setActive(true);
        assertUserResponse(userService.updateUser(100, request));

        request = new UserRequest().setUsername("test").setPassword("test").setRole("ADMIN").setActive(false);
        assertUserResponse(userService.updateUser(100, request));

        request = new UserRequest().setUsername("test").setPassword("test").setRole("ADMIN").setActive(true);
        assertUserResponse(userService.updateUser(100, request));
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
        when(userRepository.countByStatusAndRole_role(any(StatusEnum.class), any(RolesEnum.class))).thenReturn(1L);

        assertThrows(ApplicationException.class, () -> userService.updateUser(100, new UserRequest().setUsername("test").setRole("USER")));
        assertThrows(ApplicationException.class, () -> userService.updateUser(100, new UserRequest().setUsername("test").setActive(false)));
    }

    /**
     * Delete an existing user with role USER
     * @throws ApplicationException - Application exception
     */
    @Test
    void whenDeleteExistingUserWithRoleUSer_thenDoNothing() throws ApplicationException {

        Role role = new Role().setId(1).setRole(RolesEnum.USER);
        User user = new User().setId(100).setUsername("test").setPassword("test").setRole(role).setStatus(StatusEnum.ACTIVE);

        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(any(User.class));

        assertDoesNotThrow(() -> userService.deleteUser(100));
    }

    /**
     * Delete an existing user with role ADMIN
     */
    @Test
    void whenDeleteExistingUserWithRoleAdmin_thenDoNothing() {

        Role role = new Role().setId(1).setRole(RolesEnum.ADMIN);
        User user = new User().setId(100).setUsername("test").setPassword("test").setRole(role).setStatus(StatusEnum.ACTIVE);

        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(userRepository.countByStatusAndRole_role(any(StatusEnum.class), any(RolesEnum.class))).thenReturn(3L);
        doNothing().when(userRepository).delete(any(User.class));

        assertDoesNotThrow(() -> userService.deleteUser(100));
    }

    /**
     * Delete a unique existing user with role ADMIN
     */
    @Test
    void whenDeleteUniqueExistingUserWithRoleAdmin_thenThrowException() {

        Role role = new Role().setId(1).setRole(RolesEnum.ADMIN);
        User user = new User().setId(100).setUsername("test").setPassword("test").setRole(role).setStatus(StatusEnum.ACTIVE);

        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(userRepository.countByStatusAndRole_role(any(StatusEnum.class), any(RolesEnum.class))).thenReturn(1L);

        assertThrows(ApplicationException.class, () -> userService.deleteUser(100));
    }

    /**
     * Delete a non-existing user
     */
    @Test
    void whenDeleteNotExistingUser_thenThrowException() {

        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        assertThrows(ApplicationException.class, () -> userService.deleteUser(100));
    }

    /**
     * Get all users
     * @throws IOException - IO exception
     */
    @Test
    void whenGetAllUsers_thenReturnUserResponseList() throws IOException {

        prepareUserRequest();
        QueryUserRequest queryUserRequest = new QueryUserRequest().setUsername("test").setRole("ADMIN")
                .setDeleted(false).setStatus("ACTIVE").setCreatedDateFrom(new Timestamp(new Date().getTime()))
                .setCreatedDateTo(new Timestamp(new Date().getTime()));

        Map<String, SortsEnum> sorts = Map.of("username", SortsEnum.ASC);
        DataQueryRequest<QueryUserRequest> userRequest = new DataQueryRequest<>();
        userRequest.setQuery(queryUserRequest).setPage(0).setSize(10).setSorts(sorts);

        Page<UserResponse> userResponsePage = userService.getAllUsers(userRequest);

        assertNotNull(userResponsePage);
        assertEquals(userResponsePage.getTotalElements(), 3);
        assertTrue(userResponsePage.hasContent());
    }

    /**
     * Get all users with no filters
     * @throws IOException - IO exception
     */
    @Test
    void whenGetAllUsersWithNoFilters_thenReturnAllUserResponseList() throws IOException {

        prepareUserRequest();
        QueryUserRequest queryUserRequest = new QueryUserRequest();

        DataQueryRequest<QueryUserRequest> userRequest = new DataQueryRequest<>();
        userRequest.setQuery(queryUserRequest).setPage(0).setSize(10);

        Page<UserResponse> userResponsePage = userService.getAllUsers(userRequest);

        assertNotNull(userResponsePage);
        assertEquals(userResponsePage.getTotalElements(), 3);
        assertTrue(userResponsePage.hasContent());
    }

    /**
     * Get all users with no data found
     */
    @Test
    void whenGetAllUsersWithNoDataFound_thenThrowException() {

        QueryUserRequest queryUserRequest = new QueryUserRequest();

        DataQueryRequest<QueryUserRequest> userRequest = new DataQueryRequest<>();
        userRequest.setQuery(queryUserRequest).setPage(0).setSize(10);

        when(userRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of()));

        assertThrows(ApplicationException.class, () -> userService.getAllUsers(userRequest));
    }

    /**
     * Assert user response
     * @param response - User response
     */
    private void assertUserResponse(UserResponse response) {
        assertNotNull(response);
        assertEquals(response.getId(), 100);
        assertEquals(response.getUsername(), "test");
    }

    /**
     * Prepare user request
     * @throws IOException - IO exception
     */
    @SuppressWarnings("unchecked")
    private void prepareUserRequest() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<User> userList = objectMapper.readValue(new File("src/test/resources/json/user-list.json"), new TypeReference<List<User>>() {});

        Page<User> userPageResponse = new PageImpl<>(userList);
        Role role = new Role().setId(1).setRole(RolesEnum.ADMIN);

        when(roleRepository.findByRole(any(RolesEnum.class))).thenReturn(Optional.of(role));
        when(userRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(userPageResponse);
        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPageResponse);
    }

    /**
     * Reset mocks
     */
    @AfterEach
    void resetMocks() {
        reset(roleRepository, userRepository, passwordEncoder);
    }
}
