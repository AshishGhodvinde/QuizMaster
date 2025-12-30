package com.quiz.application.model;

public enum QuestionType {
    SINGLE_CORRECT("Single Correct"),
    MULTIPLE_CORRECT("Multiple Correct"),
    TRUE_FALSE("True/False");

    private final String displayName;

    QuestionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
