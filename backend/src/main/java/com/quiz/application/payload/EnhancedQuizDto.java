package com.quiz.application.payload;

import com.quiz.application.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnhancedQuizDto {
    private Long id;
    private String title;
    private String description;
    private QuizCategory category;
    private Difficulty difficulty;
    private Integer timeLimitMinutes;
    private QuizStatus status;
    private QuizVisibility visibility;
    private Boolean allowMultipleAttempts;
    private Boolean randomizeQuestions;
    private Boolean randomizeOptions;
    private Boolean negativeMarking;
    private Double negativeMarks;
    private Integer passingPercentage;
    private Integer maxAttempts;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdByUsername;
    private Integer questionCount;
    private Integer totalMarks;
    private Double averageScore;
    private Integer totalAttempts;
    private List<QuestionDto> questions;
}
