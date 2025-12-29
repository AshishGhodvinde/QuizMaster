package com.quiz.application.payload;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizDto {
    private Long id;
    private String title;
    private String description;
    private String createdByUsername;
}
