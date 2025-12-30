package com.hai.quizapp.dtos.users;

import java.util.Set;

/**
 * DTO for login response with access and refresh tokens.
 */
public record LoginResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        Long expiresIn,
        UserInfo user
        ) {

    public record UserInfo(
            String email,
            String fullName,
            Set<String> roles
            ) {

    }

    public static LoginResponse of(String accessToken, String refreshToken, Long expiresIn, UserInfo user) {
        return new LoginResponse(accessToken, refreshToken, "Bearer", expiresIn, user);
    }
}
