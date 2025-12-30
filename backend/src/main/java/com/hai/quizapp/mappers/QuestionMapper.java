package com.hai.quizapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.hai.quizapp.dtos.QuestionRequest;
import com.hai.quizapp.dtos.QuestionResponse;
import com.hai.quizapp.entities.Question;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {AnswerMapper.class})
public interface QuestionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "answers", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Question toEntity(QuestionRequest request);

    QuestionResponse toResponse(Question question);
}
