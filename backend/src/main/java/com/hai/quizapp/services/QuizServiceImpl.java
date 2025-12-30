package com.hai.quizapp.services;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hai.quizapp.dtos.QuizRequest;
import com.hai.quizapp.dtos.QuizResponse;
import com.hai.quizapp.entities.Question;
import com.hai.quizapp.entities.Quiz;
import com.hai.quizapp.exceptions.ResourceNotFoundException;
import com.hai.quizapp.mappers.QuizMapper;
import com.hai.quizapp.repositories.QuestionRepository;
import com.hai.quizapp.repositories.QuizRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizServiceImpl implements QuizService {

    private static final String QUIZ_NOT_FOUND = "Quiz not found with id: ";
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuizMapper quizMapper;

    @Override
    public QuizResponse createQuiz(QuizRequest request) {
        Quiz quiz = quizMapper.toEntity(request);

        if (request.questionIds() != null && !request.questionIds().isEmpty()) {
            Set<Question> questions = new HashSet<>();
            for (UUID questionId : request.questionIds()) {
                Question question = questionRepository.findById(questionId)
                        .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + questionId));
                questions.add(question);
            }
            quiz.setQuestions(questions);
        }

        Quiz savedQuiz = quizRepository.save(quiz);
        return quizMapper.toResponse(savedQuiz);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuizResponse> getAllQuizzes(Pageable pageable) {
        return quizRepository.findByActiveTrue(pageable)
                .map(quizMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public QuizResponse getQuizById(UUID id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(QUIZ_NOT_FOUND + id));

        if (Boolean.FALSE.equals(quiz.getActive())) {
            throw new ResourceNotFoundException(QUIZ_NOT_FOUND + id);
        }

        return quizMapper.toResponse(quiz);
    }

    @Override
    public QuizResponse updateQuiz(UUID id, QuizRequest request) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(QUIZ_NOT_FOUND + id));

        if (Boolean.FALSE.equals(quiz.getActive())) {
            throw new ResourceNotFoundException(QUIZ_NOT_FOUND + id);
        }

        quiz.setTitle(request.title());
        quiz.setDescription(request.description());
        quiz.setDurationMinutes(request.durationMinutes());

        if (request.questionIds() != null) {
            Set<Question> questions = new HashSet<>();
            for (UUID questionId : request.questionIds()) {
                Question question = questionRepository.findById(questionId)
                        .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + questionId));
                questions.add(question);
            }
            quiz.setQuestions(questions);
        }

        Quiz updatedQuiz = quizRepository.save(quiz);
        return quizMapper.toResponse(updatedQuiz);
    }

    @Override
    public void softDeleteQuiz(UUID id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(QUIZ_NOT_FOUND + id));
        quiz.setActive(false);
        quizRepository.save(quiz);
    }

    @Override
    public QuizResponse addQuestionsToQuiz(UUID quizId, Set<UUID> questionIds) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException(QUIZ_NOT_FOUND + quizId));

        for (UUID questionId : questionIds) {
            Question question = questionRepository.findById(questionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + questionId));
            quiz.getQuestions().add(question);
        }

        Quiz updatedQuiz = quizRepository.save(quiz);
        return quizMapper.toResponse(updatedQuiz);
    }

    @Override
    public QuizResponse removeQuestionsFromQuiz(UUID quizId, Set<UUID> questionIds) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException(QUIZ_NOT_FOUND + quizId));

        quiz.getQuestions().removeIf(question -> questionIds.contains(question.getId()));

        Quiz updatedQuiz = quizRepository.save(quiz);
        return quizMapper.toResponse(updatedQuiz);
    }
}
