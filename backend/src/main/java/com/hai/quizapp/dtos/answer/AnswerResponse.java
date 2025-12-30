package com.hai.quizapp.dtos.answer;

import java.util.UUID;

public record AnswerResponse(
        UUID id,
        String content,
        Boolean isCorrect
        ) {

}
