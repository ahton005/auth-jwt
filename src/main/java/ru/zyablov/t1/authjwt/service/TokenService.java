package ru.zyablov.t1.authjwt.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface TokenService {
    String generateToken(UserDetails userDetails);

    String getUsername(String token);

    List<String> getAuthorities(String token);
}
