package com.hai.quizapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.hai.quizapp.dtos.RoleResponse;
import com.hai.quizapp.entities.Role;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {

    RoleResponse toResponse(Role role);
}
