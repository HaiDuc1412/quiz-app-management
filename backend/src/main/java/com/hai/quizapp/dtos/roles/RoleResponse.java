package com.hai.quizapp.dtos.roles;

import java.util.UUID;

import com.hai.quizapp.enums.RoleEnum;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Role information response")
public record RoleResponse(
        @Schema(description = "Role ID", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID id,
        @Schema(description = "Role name", example = "ROLE_ADMIN")
        RoleEnum name,
        @Schema(description = "Role description", example = "Administrator with full access")
        String description
        ) {

}
