package com.hai.quizapp.services;

import com.hai.quizapp.dtos.submissions.ExamResultResponse;
import com.hai.quizapp.dtos.submissions.ExamSubmitRequest;

public interface ExamService {

    ExamResultResponse submitExam(ExamSubmitRequest request);
}
