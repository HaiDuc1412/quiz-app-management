package com.hai.quizapp.dtos;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record QuizResponse(
        UUID id,
        String title,
        String description,
        Integer durationMinutes,
        Boolean active,
        Set<QuestionResponse> questions,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
        ) {

}
