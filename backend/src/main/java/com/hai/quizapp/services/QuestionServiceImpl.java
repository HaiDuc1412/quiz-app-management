package com.hai.quizapp.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hai.quizapp.dtos.QuestionRequest;
import com.hai.quizapp.dtos.QuestionResponse;
import com.hai.quizapp.entities.Answer;
import com.hai.quizapp.entities.Question;
import com.hai.quizapp.exceptions.ResourceNotFoundException;
import com.hai.quizapp.mappers.AnswerMapper;
import com.hai.quizapp.mappers.QuestionMapper;
import com.hai.quizapp.repositories.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private static final String QUESTION_NOT_FOUND = "Question not found with id: ";
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;

    @Override
    public QuestionResponse createQuestion(QuestionRequest request) {
        Question question = questionMapper.toEntity(request);

        request.answers().forEach(answerRequest -> {
            Answer answer = answerMapper.toEntity(answerRequest);
            answer.setQuestion(question);
            question.getAnswers().add(answer);
        });

        Question savedQuestion = questionRepository.save(question);
        return questionMapper.toResponse(savedQuestion);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuestionResponse> getAllQuestions(Pageable pageable) {
        return questionRepository.findByIsActiveTrue(pageable)
                .map(questionMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionResponse getQuestionById(UUID id) {
        Question question = questionRepository.findByIdWithAnswers(id)
                .orElseThrow(() -> new ResourceNotFoundException(QUESTION_NOT_FOUND + id));

        if (Boolean.FALSE.equals(question.getIsActive())) {
            throw new ResourceNotFoundException(QUESTION_NOT_FOUND + id);
        }

        return questionMapper.toResponse(question);
    }

    @Override
    public QuestionResponse updateQuestion(UUID id, QuestionRequest request) {
        Question question = questionRepository.findByIdWithAnswers(id)
                .orElseThrow(() -> new ResourceNotFoundException(QUESTION_NOT_FOUND + id));

        if (Boolean.FALSE.equals(question.getIsActive())) {
            throw new ResourceNotFoundException(QUESTION_NOT_FOUND + id);
        }

        question.setContent(request.content());
        question.setType(request.type());
        question.setScore(request.score());

        question.getAnswers().clear();

        request.answers().forEach(answerRequest -> {
            Answer answer = answerMapper.toEntity(answerRequest);
            answer.setQuestion(question);
            question.getAnswers().add(answer);
        });

        Question updatedQuestion = questionRepository.save(question);
        return questionMapper.toResponse(updatedQuestion);
    }

    @Override
    public void softDeleteQuestion(UUID id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(QUESTION_NOT_FOUND + id));

        question.setIsActive(false);
        questionRepository.save(question);
    }
}
