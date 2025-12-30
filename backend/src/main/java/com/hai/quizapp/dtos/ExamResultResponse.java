package com.hai.quizapp.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record ExamResultResponse(
        UUID submissionId,
        UUID quizId,
        String quizTitle,
        UUID userId,
        Double score,
        Integer totalQuestions,
        Integer correctAnswers,
        Integer incorrectAnswers,
        Double percentage,
        Boolean passed,
        LocalDateTime submissionTime
        ) {

}
