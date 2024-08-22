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

        return LoginUserResponse.builder()
                .token(generateToken(authenticatedUser))
                .expiresIn(jwtService.getTokenExpiration())
                .status(authenticatedUser.getStatus())
                .role(authenticatedUser.getRole().getRole())
                .build();
    }

    public String refreshToken(String token, String path) {

        String userName = jwtService.getUserName(token);

        Token tokenEntity = tokenRepository.findByTokenAndUsername(token, userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token"));

        tokenEntity.setValid(false);
        tokenEntity.setPath(path);
        tokenRepository.saveAndFlush(tokenEntity);

        return generateToken(tokenEntity.getUser());
    }

    public void logout(String authHeader, String path) {

        if (null == authHeader) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token is required");
        }

        final String jwt = authHeader.substring(7);
        String username = jwtService.getUserName(jwt);

        Token token = tokenRepository.findByTokenAndUsername(jwt, username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Token not found"));

        if (!token.isValid()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token is already invalid");
        }

        token.setValid(false);
        token.setPath(path);
        tokenRepository.saveAndFlush(token);
    }

    private String generateToken(User authenticatedUser) {
        String jwtToken = jwtService.generateToken(authenticatedUser);

        Token token = new Token(jwtToken, authenticatedUser);
        tokenRepository.save(token);

        return jwtToken;
    }

}
