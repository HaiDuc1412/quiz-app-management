package com.hai.quizapp.services;

import com.hai.quizapp.dtos.ExamResultResponse;
import com.hai.quizapp.dtos.ExamSubmitRequest;

public interface ExamService {

    ExamResultResponse submitExam(ExamSubmitRequest request);
}
