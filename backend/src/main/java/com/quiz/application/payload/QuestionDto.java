package com.quiz.application.payload;

import com.quiz.application.model.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    private Long id;
    private String questionText;
    private QuestionType questionType;
    private List<String> options;
    private String correctAnswer;
    private List<String> correctAnswers; // For multiple correct
    private String explanation;
    private Integer questionOrder;
    private Integer marks;
    private Long quizId;
}
