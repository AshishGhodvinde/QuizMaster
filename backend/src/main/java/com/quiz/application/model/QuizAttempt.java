package com.quiz.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quiz_attempts")
public class QuizAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    @JsonIgnore
    private Quiz quiz;

    @Column(name = "total_questions")
    private Integer totalQuestions;

    @Column(name = "correct_answers")
    private Integer correctAnswers;

    @Column(name = "incorrect_answers")
    private Integer incorrectAnswers;

    @Column(name = "unattempted_questions")
    private Integer unattemptedQuestions;

    @Column(nullable = false)
    private Integer totalMarks;

    @Column(name = "obtained_marks")
    private Double obtainedMarks;

    @Column(name = "negative_marks")
    private Double negativeMarks = 0.0;

    @Column(nullable = false)
    private Double percentage;

    @Column(name = "time_taken_seconds")
    private Integer timeTakenSeconds;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "is_completed")
    private Boolean isCompleted = false;

    @Column(name = "is_passed")
    private Boolean isPassed = false;

    @Column(name = "attempt_number")
    private Integer attemptNumber = 1;

    @Column(name = "answers_data", columnDefinition = "TEXT")
    private String answersData; // JSON string of user answers

    @PrePersist
    protected void onCreate() {
        if (startedAt == null) {
            startedAt = LocalDateTime.now();
        }
        if (submittedAt == null) {
            submittedAt = LocalDateTime.now();
        }
        calculateResults();
    }

    @PreUpdate
    protected void onUpdate() {
        if (submittedAt != null && startedAt != null) {
            timeTakenSeconds = (int) java.time.Duration.between(startedAt, submittedAt).getSeconds();
        }
        calculateResults();
    }

    private void calculateResults() {
        if (totalMarks != null && totalMarks > 0 && obtainedMarks != null) {
            percentage = (obtainedMarks / totalMarks) * 100;
            
            if (quiz != null && quiz.getPassingPercentage() != null) {
                isPassed = percentage >= quiz.getPassingPercentage();
            }
        }
        
        if (totalQuestions != null && correctAnswers != null && incorrectAnswers != null) {
            unattemptedQuestions = totalQuestions - (correctAnswers + incorrectAnswers);
        }
    }
}