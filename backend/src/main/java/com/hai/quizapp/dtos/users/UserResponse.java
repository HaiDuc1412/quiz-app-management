package com.hai.quizapp.dtos.users;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.hai.quizapp.dtos.roles.RoleResponse;

public record UserResponse(
        UUID id,
        String email,
        String fullName,
        Boolean active,
        Set<RoleResponse> roles,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
        ) {

}
