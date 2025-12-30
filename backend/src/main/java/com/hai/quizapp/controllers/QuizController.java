package com.hai.quizapp.controllers;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hai.quizapp.dtos.ApiResponse;
import com.hai.quizapp.dtos.QuizRequest;
import com.hai.quizapp.dtos.QuizResponse;
import com.hai.quizapp.services.QuizService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/quizzes")
@RequiredArgsConstructor
@Validated
@Tag(name = "Quiz Management", description = "APIs for managing quizzes")
public class QuizController {

    private final QuizService quizService;

    @Operation(summary = "Create a new quiz", description = "Creates a new quiz with questions")
    @PostMapping
    public ResponseEntity<ApiResponse<QuizResponse>> createQuiz(@Valid @RequestBody QuizRequest request) {
        QuizResponse response = quizService.createQuiz(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(response, "Quiz created successfully"));
    }

    @Operation(summary = "Get all quizzes", description = "Retrieves all active quizzes with pagination")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<QuizResponse>>> getAllQuizzes(
            @Parameter(description = "Page number (0-based)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field")
            @RequestParam(defaultValue = "createdAt") String sort,
            @Parameter(description = "Sort direction")
            @RequestParam(defaultValue = "DESC") String direction) {

        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        Page<QuizResponse> quizzes = quizService.getAllQuizzes(pageable);
        return ResponseEntity.ok(ApiResponse.success(quizzes));
    }

    @Operation(summary = "Get quiz by ID", description = "Retrieves a specific quiz with all questions")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<QuizResponse>> getQuizById(@PathVariable UUID id) {
        QuizResponse response = quizService.getQuizById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "Update quiz", description = "Updates an existing quiz")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<QuizResponse>> updateQuiz(
            @PathVariable UUID id,
            @Valid @RequestBody QuizRequest request) {
        QuizResponse response = quizService.updateQuiz(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Quiz updated successfully"));
    }

    @Operation(summary = "Add questions to quiz", description = "Adds questions to an existing quiz")
    @PostMapping("/{id}/questions")
    public ResponseEntity<ApiResponse<QuizResponse>> addQuestions(
            @PathVariable UUID id,
            @RequestBody Set<UUID> questionIds) {
        QuizResponse response = quizService.addQuestionsToQuiz(id, questionIds);
        return ResponseEntity.ok(ApiResponse.success(response, "Questions added successfully"));
    }

    @Operation(summary = "Remove questions from quiz", description = "Removes questions from a quiz")
    @DeleteMapping("/{id}/questions")
    public ResponseEntity<ApiResponse<QuizResponse>> removeQuestions(
            @PathVariable UUID id,
            @RequestBody Set<UUID> questionIds) {
        QuizResponse response = quizService.removeQuestionsFromQuiz(id, questionIds);
        return ResponseEntity.ok(ApiResponse.success(response, "Questions removed successfully"));
    }

    @Operation(summary = "Delete quiz (Soft Delete)", description = "Soft deletes a quiz")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteQuiz(@PathVariable UUID id) {
        quizService.softDeleteQuiz(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Quiz deleted successfully"));
    }
}
