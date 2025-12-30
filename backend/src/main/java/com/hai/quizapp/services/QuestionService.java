package com.hai.quizapp.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hai.quizapp.dtos.questions.QuestionRequest;
import com.hai.quizapp.dtos.questions.QuestionResponse;

public interface QuestionService {

    QuestionResponse createQuestion(QuestionRequest request);

    Page<QuestionResponse> getAllQuestions(Pageable pageable);

    QuestionResponse getQuestionById(UUID id);

    QuestionResponse updateQuestion(UUID id, QuestionRequest request);

    void softDeleteQuestion(UUID id);
}
