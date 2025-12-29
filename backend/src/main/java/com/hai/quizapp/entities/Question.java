package com.hai.quizapp.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.hai.quizapp.enums.QuestionType;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType questionType;

    @Column(nullable = false)
    private Integer score;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();
}
