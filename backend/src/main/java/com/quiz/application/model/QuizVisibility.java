package com.quiz.application.model;

public enum QuizVisibility {
    PUBLIC("Public"),
    PRIVATE("Private");

    private final String displayName;

    QuizVisibility(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
