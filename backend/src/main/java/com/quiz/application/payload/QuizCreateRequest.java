package com.quiz.application.payload;

import com.quiz.application.model.Difficulty;
import com.quiz.application.model.QuizCategory;
import com.quiz.application.model.QuizVisibility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizCreateRequest {
    private String title;
    private String description;
    private QuizCategory category;
    private Difficulty difficulty;
    private Integer timeLimitMinutes;
    private QuizVisibility visibility;
    private Boolean allowMultipleAttempts;
    private Boolean randomizeQuestions;
    private Boolean randomizeOptions;
    private Boolean negativeMarking;
    private Double negativeMarks;
    private Integer passingPercentage;
    private Integer maxAttempts;
}
