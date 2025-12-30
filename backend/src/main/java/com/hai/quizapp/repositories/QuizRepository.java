package com.hai.quizapp.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hai.quizapp.entities.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, UUID> {

    @EntityGraph(attributePaths = {"questions", "questions.answers"})
    @Override
    Optional<Quiz> findById(UUID id);

    @EntityGraph(attributePaths = {"questions"})
    Page<Quiz> findByActiveTrue(Pageable pageable);

    @Override
    Page<Quiz> findAll(Pageable pageable);
}
