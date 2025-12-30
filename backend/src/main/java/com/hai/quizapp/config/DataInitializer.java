package com.hai.quizapp.config;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hai.quizapp.constants.Constants;
import com.hai.quizapp.entities.Answer;
import com.hai.quizapp.entities.Question;
import com.hai.quizapp.entities.Quiz;
import com.hai.quizapp.entities.Role;
import com.hai.quizapp.entities.User;
import com.hai.quizapp.enums.QuestionType;
import com.hai.quizapp.repositories.QuestionRepository;
import com.hai.quizapp.repositories.QuizRepository;
import com.hai.quizapp.repositories.RoleRepository;
import com.hai.quizapp.repositories.UserRepository;

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
    private final QuizRepository quizRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            initRoles();
            initSampleUsers();
            initSampleQuestions();
            initSampleQuizzes();
        };
    }

    /**
     * Initialize default roles
     */
    private void initRoles() {
        if (!roleRepository.existsByName(Constants.ROLE_ADMIN)) {
            Role adminRole = new Role();
            adminRole.setName(Constants.ROLE_ADMIN);
            adminRole.setDescription("Administrator with full access");
            roleRepository.save(adminRole);
            log.info("Created default role: {}", Constants.ROLE_ADMIN);
        }

        if (!roleRepository.existsByName(Constants.ROLE_USER)) {
            Role userRole = new Role();
            userRole.setName(Constants.ROLE_USER);
            userRole.setDescription("Regular user who can take quizzes");
            roleRepository.save(userRole);
            log.info("Created default role: {}", Constants.ROLE_USER);
        }
    }

    /**
     * Initialize sample users
     */
    private void initSampleUsers() {
        if (userRepository.count() > 0) {
            log.info("Database already contains users. Skipping user initialization.");
            return;
        }

        Role adminRole = roleRepository.findByName(Constants.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Admin role not found"));
        Role userRole = roleRepository.findByName(Constants.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("User role not found"));

        // Create admin user
        User admin = User.builder()
                .email("admin@quizapp.com")
                .password(passwordEncoder.encode("Admin@123"))
                .fullName("Admin User")
                .active(true)
                .roles(Set.of(adminRole))
                .build();
        userRepository.save(admin);
        log.info("Created admin user: admin@quizapp.com / Admin@123");

        // Create regular user
        User user = User.builder()
                .email("user@quizapp.com")
                .password(passwordEncoder.encode("User@123"))
                .fullName("Regular User")
                .active(true)
                .roles(Set.of(userRole))
                .build();
        userRepository.save(user);
        log.info("Created regular user: user@quizapp.com / User@123");
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

    /**
     * Initialize sample quizzes
     */
    private void initSampleQuizzes() {
        if (quizRepository.count() > 0) {
            log.info("Database already contains quizzes. Skipping quiz initialization.");
            return;
        }

        List<Question> allQuestions = questionRepository.findAll();

        if (allQuestions.isEmpty()) {
            log.warn("No questions available to create quizzes.");
            return;
        }

        // Create Java Basics Quiz
        Quiz javaQuiz = Quiz.builder()
                .title("Java Programming Basics")
                .description("Test your knowledge of Java fundamentals")
                .durationMinutes(30)
                .active(true)
                .questions(Set.copyOf(allQuestions.subList(0, Math.min(3, allQuestions.size()))))
                .build();
        quizRepository.save(javaQuiz);
        log.info("Created sample quiz: Java Programming Basics");

        // Create General Knowledge Quiz
        if (allQuestions.size() > 3) {
            Quiz generalQuiz = Quiz.builder()
                    .title("General Knowledge Quiz")
                    .description("Mixed questions to test your general knowledge")
                    .durationMinutes(20)
                    .active(true)
                    .questions(Set.copyOf(allQuestions.subList(3, Math.min(5, allQuestions.size()))))
                    .build();
            quizRepository.save(generalQuiz);
            log.info("Created sample quiz: General Knowledge Quiz");
        }
    }
}
