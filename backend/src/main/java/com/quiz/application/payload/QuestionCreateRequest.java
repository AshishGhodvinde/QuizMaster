package com.quiz.application.payload;

import com.quiz.application.model.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionCreateRequest {
    private String questionText;
    private QuestionType questionType;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String optionE;
    private String optionF;
    private String correctAnswer;
    private String correctAnswers; // For multiple correct answers
    private String explanation;
    private Integer questionOrder;
    private Integer marks;
}
