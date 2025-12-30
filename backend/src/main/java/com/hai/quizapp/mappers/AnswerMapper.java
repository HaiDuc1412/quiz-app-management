package com.hai.quizapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.hai.quizapp.dtos.AnswerRequest;
import com.hai.quizapp.dtos.AnswerResponse;
import com.hai.quizapp.entities.Answer;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnswerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Answer toEntity(AnswerRequest request);

    AnswerResponse toResponse(Answer answer);
}
