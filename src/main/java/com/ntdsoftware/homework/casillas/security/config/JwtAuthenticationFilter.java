package com.ntdsoftware.homework.casillas.security.config;

import com.ntdsoftware.homework.casillas.admin.exception.UserAccountIsDisabledException;
import com.ntdsoftware.homework.casillas.common.exception.ApplicationException;
import com.ntdsoftware.homework.casillas.security.service.AuthenticationService;
import com.ntdsoftware.homework.casillas.security.service.JWTService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;

    private final JWTService jwtService;

    private final UserDetailsService userDetailsService;

    private final AuthenticationService authenticationService;

    @Value("${security.authentication.token.header}")
    protected String authorizationHeader;

    @Value("${security.authentication.token.prefix}")
    protected String tokenPrefix;

    public JwtAuthenticationFilter(HandlerExceptionResolver handlerExceptionResolver,
                                   JWTService jwtService, UserDetailsService userDetailsService,
                                   AuthenticationService authenticationService) {

        this.handlerExceptionResolver = handlerExceptionResolver;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.authenticationService = authenticationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader(authorizationHeader);

        if (null == authHeader || !authHeader.startsWith(tokenPrefix)) {
            log.info("JWT token not found in request");
            filterChain.doFilter(request, response);
            return;
        }

        try {

            final String jwt = authHeader.substring(7);
            final String userName = jwtService.getUserName(jwt);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (null != userName && null == authentication) {

                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);

                log.info("Account is enabled: {}", userDetails.isEnabled());

                if (!userDetails.isEnabled()) {
                    throw new UserAccountIsDisabledException(userDetails.getUsername());
                }

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    if (!request.getRequestURI().contains("/auth/logout")) {
                        String newToken = authenticationService.refreshToken(jwt, request.getRequestURI());
                        response.setHeader(authorizationHeader, newToken);

                        log.info("User {} authenticated", userName);
                    }
                }
            }

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException expiredJwtException) {
            log.error("JWT token has expired");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            handlerExceptionResolver.resolveException(request, response, null, expiredJwtException);

        } catch (ApplicationException applicationException) {
            log.error(applicationException.getMessage());
            response.setStatus(applicationException.getHttpStatus().value());
            handlerExceptionResolver.resolveException(request, response, null, applicationException);

        }
    }

}
