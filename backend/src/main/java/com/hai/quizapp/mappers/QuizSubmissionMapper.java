package com.hai.quizapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.hai.quizapp.dtos.QuizSubmissionResponse;
import com.hai.quizapp.entities.QuizSubmission;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuizSubmissionMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.fullName", target = "userFullName")
    @Mapping(source = "quiz.id", target = "quizId")
    @Mapping(source = "quiz.title", target = "quizTitle")
    QuizSubmissionResponse toResponse(QuizSubmission submission);
}
