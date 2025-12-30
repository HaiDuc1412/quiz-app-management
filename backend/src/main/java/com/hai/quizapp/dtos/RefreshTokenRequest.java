package com.hai.quizapp.dtos;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for refresh token request.
 */
public record RefreshTokenRequest(
        @NotBlank(message = "Refresh token is required")
        String refreshToken
        ) {

}
