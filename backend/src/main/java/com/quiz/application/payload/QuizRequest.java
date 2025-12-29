package com.quiz.application.payload;

import lombok.Data;

@Data
public class QuizRequest {
    private String title;
    private String description;
}