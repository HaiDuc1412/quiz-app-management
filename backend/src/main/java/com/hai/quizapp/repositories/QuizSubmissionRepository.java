package com.hai.quizapp.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hai.quizapp.entities.QuizSubmission;

@Repository
public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission, UUID> {

    @EntityGraph(attributePaths = {"user", "quiz"})
    Page<QuizSubmission> findByUserId(UUID userId, Pageable pageable);

    @EntityGraph(attributePaths = {"user", "quiz"})
    Page<QuizSubmission> findByQuizId(UUID quizId, Pageable pageable);

    @EntityGraph(attributePaths = {"user", "quiz"})
    List<QuizSubmission> findByUserIdAndQuizId(UUID userId, UUID quizId);
}
