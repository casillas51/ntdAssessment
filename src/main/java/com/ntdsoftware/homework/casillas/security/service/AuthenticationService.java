package com.ntdsoftware.homework.casillas.security.service;

import com.ntdsoftware.homework.casillas.security.controller.request.LoginUserRequest;
import com.ntdsoftware.homework.casillas.security.controller.response.LoginUserResponse;
import com.ntdsoftware.homework.casillas.security.entity.Token;
import com.ntdsoftware.homework.casillas.security.entity.User;
import com.ntdsoftware.homework.casillas.security.repository.TokenRepository;
import com.ntdsoftware.homework.casillas.security.repository.UserRepository;
import com.sun.net.httpserver.HttpsServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JWTService jwtService;

    public AuthenticationService(UserRepository userRepository, TokenRepository tokenRepository,
                                 BCryptPasswordEncoder passwordEncoder, JWTService jwtService) {

        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginUserResponse authenticate(LoginUserRequest userRequest) {

        if (null == userRequest.getUsername() || null == userRequest.getPassword()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username and password are required");
        }

        User authenticatedUser = userRepository.findByUsername(userRequest.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + userRequest.getUsername()));

        if (!passwordEncoder.matches(userRequest.getPassword(), authenticatedUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }

        String jwtToken = jwtService.generateToken(authenticatedUser);

        Token token = new Token(jwtToken, authenticatedUser);
        tokenRepository.save(token);

        return LoginUserResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getTokenExpiration())
                .status(authenticatedUser.getStatus())
                .role(authenticatedUser.getRole().getRole())
                .build();
    }

    public void logout(String authHeader) {

        if (null == authHeader) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token is required");
        }

        final String jwt = authHeader.substring(7);
        String username = jwtService.getUserName(jwt);

        Token token = tokenRepository.findByTokenAndUsername(jwt, username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Token not found"));

        if (!token.isValid()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token is already invalid");
        }

        token.setValid(false);
        tokenRepository.saveAndFlush(token);
    }

}
