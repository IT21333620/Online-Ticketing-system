package com.example.onlineticketingsystem.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JWTGenerator jwtGenerator;

    public OAuth2AuthenticationSuccessHandler(JWTGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // Get the OAuth2User object from the authentication
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // Generate JWT for the OAuth2User
        String jwtToken = jwtGenerator.generateTokenForOAuth2User(oAuth2User);

        // You can set the JWT as a response header or return it in the response body
        response.setHeader("Authorization", "Bearer " + jwtToken);

        // Optionally, redirect to a front-end or homepage after successful login
        response.sendRedirect("http://localhost:8080/sucess");
    }
}
