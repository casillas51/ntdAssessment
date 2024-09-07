package com.ntdsoftware.homework.casillas.security.service;

import com.ntdsoftware.homework.casillas.admin.exception.UserAccountIsDisabledException;
import com.ntdsoftware.homework.casillas.admin.exception.UserNotFoundException;
import com.ntdsoftware.homework.casillas.common.exception.ApplicationException;
import com.ntdsoftware.homework.casillas.security.controller.request.LoginUserRequest;
import com.ntdsoftware.homework.casillas.security.controller.response.LoginUserResponse;
import com.ntdsoftware.homework.casillas.common.entity.Token;
import com.ntdsoftware.homework.casillas.common.entity.User;
import com.ntdsoftware.homework.casillas.security.repository.TokenRepository;
import com.ntdsoftware.homework.casillas.common.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * This service class is responsible for handling authentication related operations.
 * It provides methods for user authentication, token refreshment, and user logout.
 */
@Service
@Slf4j
public class AuthenticationService {

    /**
     * Repository for User related database operations.
     */
    private final UserRepository userRepository;

    /**
     * Repository for Token related database operations.
     */
    private final TokenRepository tokenRepository;

    /**
     * Encoder for password encryption.
     */
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Service for JWT related operations.
     */
    private final JWTService jwtService;

    /**
     * Constructor for AuthenticationService.
     *
     * @param userRepository Repository for User related database operations.
     * @param tokenRepository Repository for Token related database operations.
     * @param passwordEncoder Encoder for password encryption.
     * @param jwtService Service for JWT related operations.
     */
    public AuthenticationService(UserRepository userRepository, TokenRepository tokenRepository,
                                 BCryptPasswordEncoder passwordEncoder, JWTService jwtService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    /**
     * Authenticates a user with the provided credentials.
     *
     * @param userRequest The user's login credentials.
     * @return LoginUserResponse containing the user's token, expiration time, status, and role.
     */
    public LoginUserResponse authenticate(LoginUserRequest userRequest) {

        if (null == userRequest.getUsername() || null == userRequest.getPassword()) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Username and password are required");
        }

        User authenticatedUser = userRepository.findByUsername(userRequest.getUsername())
                .orElseThrow(() -> new UserNotFoundException(userRequest.getUsername()));

        if (!passwordEncoder.matches(userRequest.getPassword(), authenticatedUser.getPassword())) {
            throw new ApplicationException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }

        if (!authenticatedUser.isEnabled()) {
            throw new UserAccountIsDisabledException(authenticatedUser.getUsername());
        }

        return LoginUserResponse.builder()
                .token(generateToken(authenticatedUser))
                .expiresIn(jwtService.getTokenExpiration())
                .status(authenticatedUser.getStatus())
                .role(authenticatedUser.getRole().getRole())
                .build();
    }

    /**
     * Refreshes a user's token.
     *
     * @param token The user's current token.
     * @param path The path for the request.
     * @return The new token.
     */
    public String refreshToken(String token, String path) {

        String userName = jwtService.getUserName(token);

        Token tokenEntity = tokenRepository.findByTokenAndUsername(token, userName)
                .orElseThrow(() -> new ApplicationException(HttpStatus.UNAUTHORIZED, "Invalid token"));

        tokenEntity.setValid(false);
        tokenEntity.setPath(path);
        tokenRepository.saveAndFlush(tokenEntity);

        return generateToken(tokenEntity.getUser());
    }

    /**
     * Logs out a user by invalidating their token.
     *
     * @param authHeader The user's authorization header containing their token.
     * @param path The path for the request.
     */
    public void logout(String authHeader, String path) {

        if (null == authHeader) {
            throw new ApplicationException(HttpStatus.UNAUTHORIZED, "Token is required");
        }

        final String jwt = authHeader.substring(7);
        String username = jwtService.getUserName(jwt);

        Token token = tokenRepository.findByTokenAndUsername(jwt, username)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "Token not found"));

        if (!token.isValid()) {
            throw new ApplicationException(HttpStatus.UNAUTHORIZED, "Token is already invalid");
        }

        token.setValid(false);
        token.setPath(path);
        tokenRepository.saveAndFlush(token);
    }

    /**
     * Generates a new token for the authenticated user.
     *
     * @param authenticatedUser The authenticated user.
     * @return The new token.
     */
    private String generateToken(User authenticatedUser) {

        String jwtToken = jwtService.generateToken(authenticatedUser);

        Token token = new Token(jwtToken, authenticatedUser);
        tokenRepository.save(token);

        return jwtToken;
    }
}