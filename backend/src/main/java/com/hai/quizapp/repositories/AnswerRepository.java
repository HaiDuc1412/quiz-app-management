package com.hai.quizapp.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hai.quizapp.entities.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, UUID> {
}
