package com.quiz.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuizCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @Column(name = "time_limit_minutes")
    private Integer timeLimitMinutes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuizStatus status = QuizStatus.PUBLISHED;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuizVisibility visibility = QuizVisibility.PRIVATE;

    @Column(name = "allow_multiple_attempts")
    private Boolean allowMultipleAttempts = true;

    @Column(name = "randomize_questions")
    private Boolean randomizeQuestions = false;

    @Column(name = "randomize_options")
    private Boolean randomizeOptions = false;

    @Column(name = "negative_marking")
    private Boolean negativeMarking = false;

    @Column(name = "negative_marks")
    private Double negativeMarks = 0.25;

    @Column(name = "passing_percentage")
    private Integer passingPercentage = 60;

    @Column(name = "max_attempts")
    private Integer maxAttempts = 1;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @JsonIgnore
    private User createdBy;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @JsonIgnore
    private List<Question> questions;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        // Set default values for nullable fields
        if (status == null) status = QuizStatus.DRAFT;
        if (visibility == null) visibility = QuizVisibility.PUBLIC;
        if (allowMultipleAttempts == null) allowMultipleAttempts = false;
        if (randomizeQuestions == null) randomizeQuestions = false;
        if (randomizeOptions == null) randomizeOptions = false;
        if (negativeMarking == null) negativeMarking = false;
        if (negativeMarks == null) negativeMarks = 0.25;
        if (passingPercentage == null) passingPercentage = 60;
        if (maxAttempts == null) maxAttempts = 1;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}