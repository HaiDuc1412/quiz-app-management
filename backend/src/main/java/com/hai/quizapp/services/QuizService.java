package com.hai.quizapp.services;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hai.quizapp.dtos.QuizRequest;
import com.hai.quizapp.dtos.QuizResponse;

public interface QuizService {

    QuizResponse createQuiz(QuizRequest request);

    Page<QuizResponse> getAllQuizzes(Pageable pageable);

    QuizResponse getQuizById(UUID id);

    QuizResponse updateQuiz(UUID id, QuizRequest request);

    void softDeleteQuiz(UUID id);

    QuizResponse addQuestionsToQuiz(UUID quizId, Set<UUID> questionIds);

    QuizResponse removeQuestionsFromQuiz(UUID quizId, Set<UUID> questionIds);
}
