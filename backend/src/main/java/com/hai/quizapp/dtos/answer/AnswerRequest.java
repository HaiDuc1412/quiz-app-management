package com.hai.quizapp.dtos.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AnswerRequest(
        @NotBlank(message = "Answer content is required")
        String content,
        @NotNull(message = "isCorrect flag is required")
        Boolean isCorrect
        ) {

}
