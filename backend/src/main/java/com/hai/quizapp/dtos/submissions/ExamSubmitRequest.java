package com.hai.quizapp.dtos.submissions;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ExamSubmitRequest(
        @NotNull(message = "Quiz ID is required")
        UUID quizId,
        @NotNull(message = "User ID is required")
        UUID userId,
        @NotEmpty(message = "Answers list cannot be empty")
        @Valid
        List<QuestionAnswerRequest> answers
        ) {

    public record QuestionAnswerRequest(
            @NotNull(message = "Question ID is required")
            UUID questionId,
            @NotEmpty(message = "Selected answer IDs cannot be empty")
            List<UUID> selectedAnswerIds
            ) {

    }
}
