package com.hai.quizapp.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hai.quizapp.dtos.UserRequest;
import com.hai.quizapp.dtos.UserResponse;

public interface UserService {

    UserResponse createUser(UserRequest request);

    Page<UserResponse> getAllUsers(Pageable pageable);

    UserResponse getUserById(UUID id);

    UserResponse updateUser(UUID id, UserRequest request);

    void softDeleteUser(UUID id);
}
