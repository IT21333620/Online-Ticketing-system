package com.example.onlineticketingsystem.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter // Lombok will generate the getter methods for email, name, and attributes
public class CustomOAuth2User implements OAuth2User {

    private final String email;
    private final String name;
    private final Map<String, Object> attributes;

    public CustomOAuth2User(String email, String name, Map<String, Object> attributes) {
        this.email = email;
        this.name = name;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return name;
    }

    // Implementing the getAuthorities method as required by OAuth2User interface
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // You can return roles or authorities based on the user's details.
        // For example, returning a default "ROLE_USER":
        return Collections.singletonList(new SimpleGrantedAuthority("passenger"));
    }
}
