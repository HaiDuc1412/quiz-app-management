package com.hai.quizapp.services;

import java.util.Set;

import org.springframework.security.core.Authentication;

import com.hai.quizapp.entities.User;

/**
 * Service interface for JWT token operations.
 */
public interface TokenService {

    /**
     * Generate JWT token for user.
     *
     * @param user the user entity
     * @param roles the user's roles
     * @return JWT token string
     */
    String generateToken(User user, Set<String> roles);

    /**
     * Extract authentication from JWT token.
     *
     * @param token the JWT token
     * @return Authentication object or null if invalid
     */
    Authentication getAuthenticationFromToken(String token);
}
