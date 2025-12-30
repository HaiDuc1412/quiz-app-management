package com.hai.quizapp.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hai.quizapp.entities.Answer;
import com.hai.quizapp.entities.Question;
import com.hai.quizapp.enums.QuestionType;
import com.hai.quizapp.repositories.QuestionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Database initializer that runs on application startup. Creates sample
 * questions and answers if they don't exist.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final QuestionRepository questionRepository;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            initSampleQuestions();
        };
    }

    /**
     * Initialize sample questions with answers for testing
     */
    private void initSampleQuestions() {
        if (questionRepository.count() > 0) {
            log.info("Database already contains questions. Skipping initialization.");
            return;
        }

        // Sample Question 1: Multiple Choice
        Question question1 = Question.builder()
                .content("What is the capital of France?")
                .type(QuestionType.MULTIPLE_CHOICE)
                .score(10)
                .isActive(true)
                .build();

        Answer answer1_1 = Answer.builder()
                .content("Paris")
                .isCorrect(true)
                .question(question1)
                .build();

        Answer answer1_2 = Answer.builder()
                .content("London")
                .isCorrect(false)
                .question(question1)
                .build();

        Answer answer1_3 = Answer.builder()
                .content("Berlin")
                .isCorrect(false)
                .question(question1)
                .build();

        Answer answer1_4 = Answer.builder()
                .content("Madrid")
                .isCorrect(false)
                .question(question1)
                .build();

        question1.getAnswers().addAll(List.of(answer1_1, answer1_2, answer1_3, answer1_4));

        // Sample Question 2: True/False
        Question question2 = Question.builder()
                .content("Java is a compiled language.")
                .type(QuestionType.MULTIPLE_CHOICE)
                .score(5)
                .isActive(true)
                .build();

        Answer answer2_1 = Answer.builder()
                .content("True")
                .isCorrect(true)
                .question(question2)
                .build();

        Answer answer2_2 = Answer.builder()
                .content("False")
                .isCorrect(false)
                .question(question2)
                .build();

        question2.getAnswers().addAll(List.of(answer2_1, answer2_2));

        // Sample Question 3: Multiple Choice
        Question question3 = Question.builder()
                .content("Which of the following is NOT a Java keyword?")
                .type(QuestionType.MULTIPLE_CHOICE)
                .score(10)
                .isActive(true)
                .build();

        Answer answer3_1 = Answer.builder()
                .content("static")
                .isCorrect(false)
                .question(question3)
                .build();

        Answer answer3_2 = Answer.builder()
                .content("Boolean")
                .isCorrect(true)
                .question(question3)
                .build();

        Answer answer3_3 = Answer.builder()
                .content("void")
                .isCorrect(false)
                .question(question3)
                .build();

        Answer answer3_4 = Answer.builder()
                .content("private")
                .isCorrect(false)
                .question(question3)
                .build();

        question3.getAnswers().addAll(List.of(answer3_1, answer3_2, answer3_3, answer3_4));

        // Sample Question 4: Multiple Choice
        Question question4 = Question.builder()
                .content("What is the default value of a boolean variable in Java?")
                .type(QuestionType.MULTIPLE_CHOICE)
                .score(5)
                .isActive(true)
                .build();

        Answer answer4_1 = Answer.builder()
                .content("true")
                .isCorrect(false)
                .question(question4)
                .build();

        Answer answer4_2 = Answer.builder()
                .content("false")
                .isCorrect(true)
                .question(question4)
                .build();

        Answer answer4_3 = Answer.builder()
                .content("null")
                .isCorrect(false)
                .question(question4)
                .build();

        Answer answer4_4 = Answer.builder()
                .content("0")
                .isCorrect(false)
                .question(question4)
                .build();

        question4.getAnswers().addAll(List.of(answer4_1, answer4_2, answer4_3, answer4_4));

        // Sample Question 5: True/False
        Question question5 = Question.builder()
                .content("Spring Boot is a framework for building microservices.")
                .type(QuestionType.MULTIPLE_CHOICE)
                .score(5)
                .isActive(true)
                .build();

        Answer answer5_1 = Answer.builder()
                .content("True")
                .isCorrect(true)
                .question(question5)
                .build();

        Answer answer5_2 = Answer.builder()
                .content("False")
                .isCorrect(false)
                .question(question5)
                .build();

        question5.getAnswers().addAll(List.of(answer5_1, answer5_2));

        // Save all questions (cascading will save answers)
        questionRepository.saveAll(List.of(question1, question2, question3, question4, question5));

        log.info("Initialized 5 sample questions with answers");
        log.info("Sample data created successfully!");
    }
}
