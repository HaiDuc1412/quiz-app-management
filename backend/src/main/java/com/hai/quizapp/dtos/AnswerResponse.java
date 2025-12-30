package com.hai.quizapp.dtos;

import java.util.UUID;

public record AnswerResponse(
        UUID id,
        String content,
        Boolean isCorrect
        ) {

}
