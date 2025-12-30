package com.hai.quizapp.dtos;

import java.util.UUID;

public record RoleResponse(
        UUID id,
        String name,
        String description
        ) {

}
