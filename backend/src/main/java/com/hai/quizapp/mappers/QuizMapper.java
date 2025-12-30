package com.hai.quizapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.hai.quizapp.dtos.quizzes.QuizRequest;
import com.hai.quizapp.dtos.quizzes.QuizResponse;
import com.hai.quizapp.entities.Quiz;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {QuestionMapper.class})
public interface QuizMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Quiz toEntity(QuizRequest request);

    QuizResponse toResponse(Quiz quiz);
}
