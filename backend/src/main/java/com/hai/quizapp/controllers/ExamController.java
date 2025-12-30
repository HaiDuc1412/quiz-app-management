package com.hai.quizapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hai.quizapp.dtos.ApiResponse;
import com.hai.quizapp.dtos.submissions.ExamResultResponse;
import com.hai.quizapp.dtos.submissions.ExamSubmitRequest;
import com.hai.quizapp.services.ExamService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/exam")
@RequiredArgsConstructor
@Validated
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Exam Management", description = "APIs for taking and submitting exams")
public class ExamController {

    private final ExamService examService;

    @Operation(
            summary = "Submit exam answers",
            description = "Submit answers for a quiz and get the result with score calculation"
    )
    @PostMapping("/submit")
    public ResponseEntity<ApiResponse<ExamResultResponse>> submitExam(
            @Valid @RequestBody ExamSubmitRequest request) {
        ExamResultResponse result = examService.submitExam(request);
        String message = result.passed()
                ? "Congratulations! You passed the exam."
                : "Sorry, you did not pass the exam.";
        return ResponseEntity.ok(ApiResponse.success(result, message));
    }
}
