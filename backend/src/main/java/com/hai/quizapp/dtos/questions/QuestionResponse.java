package com.hai.quizapp.dtos.questions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.hai.quizapp.dtos.answer.AnswerResponse;
import com.hai.quizapp.enums.QuestionType;

public record QuestionResponse(
        UUID id,
        String content,
        QuestionType type,
        Integer score,
        Boolean isActive,
        List<AnswerResponse> answers,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
        ) {

}
