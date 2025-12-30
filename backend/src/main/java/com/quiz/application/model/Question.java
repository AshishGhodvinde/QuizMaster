package com.quiz.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType questionType;

    @Column(columnDefinition = "TEXT")
    private String optionA;

    @Column(columnDefinition = "TEXT")
    private String optionB;

    @Column(columnDefinition = "TEXT")
    private String optionC;

    @Column(columnDefinition = "TEXT")
    private String optionD;

    @Column(columnDefinition = "TEXT")
    private String optionE;

    @Column(columnDefinition = "TEXT")
    private String optionF;

    @Column(name = "correct_answer")
    private String correctAnswer;

    @Column(name = "correct_answers")
    private String correctAnswers; // For multiple correct answers (comma-separated)

    @Column(columnDefinition = "TEXT")
    private String explanation;

    @Column(name = "question_order")
    private Integer questionOrder;

    @Column(nullable = false)
    private Integer marks = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    @JsonIgnore
    private Quiz quiz;
}