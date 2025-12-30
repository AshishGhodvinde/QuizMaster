package com.quiz.application.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizSubmissionRequest {
    private Map<Long, Object> answers; // questionId -> answer (String for single, List<String> for multiple)
    private Integer timeTakenSeconds;
    private Boolean isCompleted;
}