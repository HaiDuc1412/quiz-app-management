package com.hai.quizapp.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record QuizSubmissionResponse(
        UUID id,
        UUID userId,
        String userFullName,
        UUID quizId,
        String quizTitle,
        Double score,
        Integer totalQuestions,
        Integer correctAnswers,
        Boolean passed,
        LocalDateTime submissionTime
        ) {

}
