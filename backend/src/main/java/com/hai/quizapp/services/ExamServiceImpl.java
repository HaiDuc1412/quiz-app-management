package com.hai.quizapp.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hai.quizapp.dtos.submissions.ExamResultResponse;
import com.hai.quizapp.dtos.submissions.ExamSubmitRequest;
import com.hai.quizapp.entities.Answer;
import com.hai.quizapp.entities.Question;
import com.hai.quizapp.entities.Quiz;
import com.hai.quizapp.entities.QuizSubmission;
import com.hai.quizapp.entities.User;
import com.hai.quizapp.enums.QuestionType;
import com.hai.quizapp.exceptions.ResourceNotFoundException;
import com.hai.quizapp.repositories.QuestionRepository;
import com.hai.quizapp.repositories.QuizRepository;
import com.hai.quizapp.repositories.QuizSubmissionRepository;
import com.hai.quizapp.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ExamServiceImpl implements ExamService {

    private static final double PASS_PERCENTAGE = 50.0;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final QuizSubmissionRepository submissionRepository;

    @Override
    public ExamResultResponse submitExam(ExamSubmitRequest request) {
        // Validate quiz and user
        Quiz quiz = quizRepository.findById(request.quizId())
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + request.quizId()));

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.userId()));

        if (Boolean.FALSE.equals(quiz.getActive())) {
            throw new IllegalStateException("Quiz is not active");
        }

        // Calculate score
        int totalQuestions = request.answers().size();
        int correctAnswers = 0;
        double totalScore = 0.0;

        for (ExamSubmitRequest.QuestionAnswerRequest answer : request.answers()) {
            Question question = questionRepository.findByIdWithAnswers(answer.questionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + answer.questionId()));

            if (isAnswerCorrect(question, answer.selectedAnswerIds())) {
                correctAnswers++;
                totalScore += question.getScore();
            }
        }

        // Calculate percentage and pass/fail
        double percentage = (correctAnswers * 100.0) / totalQuestions;
        boolean passed = percentage >= PASS_PERCENTAGE;

        // Save submission
        QuizSubmission submission = QuizSubmission.builder()
                .user(user)
                .quiz(quiz)
                .score(totalScore)
                .totalQuestions(totalQuestions)
                .correctAnswers(correctAnswers)
                .passed(passed)
                .build();

        QuizSubmission savedSubmission = submissionRepository.save(submission);

        // Return result
        return new ExamResultResponse(
                savedSubmission.getId(),
                quiz.getId(),
                quiz.getTitle(),
                user.getId(),
                totalScore,
                totalQuestions,
                correctAnswers,
                totalQuestions - correctAnswers,
                percentage,
                passed,
                savedSubmission.getSubmissionTime()
        );
    }

    private boolean isAnswerCorrect(Question question, List<UUID> selectedAnswerIds) {
        // Get all correct answers for this question
        List<UUID> correctAnswerIds = question.getAnswers().stream()
                .filter(Answer::getIsCorrect)
                .map(Answer::getId)
                .toList();

        if (question.getType() == QuestionType.SINGLE_CHOICE) {
            // For single choice: must select exactly one correct answer
            return selectedAnswerIds.size() == 1 && correctAnswerIds.contains(selectedAnswerIds.get(0));
        } else {
            // For multiple choice: must select all correct answers and no wrong ones
            return selectedAnswerIds.size() == correctAnswerIds.size()
                    && selectedAnswerIds.containsAll(correctAnswerIds)
                    && correctAnswerIds.containsAll(selectedAnswerIds);
        }
    }
}
