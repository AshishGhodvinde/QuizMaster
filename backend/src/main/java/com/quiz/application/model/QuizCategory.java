package com.quiz.application.model;

public enum QuizCategory {
    JAVA("Java"),
    DSA("Data Structures & Algorithms"),
    APTITUDE("Aptitude"),
    GENERAL_KNOWLEDGE("General Knowledge"),
    SCIENCE("Science"),
    MATHEMATICS("Mathematics"),
    HISTORY("History"),
    GEOGRAPHY("Geography"),
    SPORTS("Sports"),
    TECHNOLOGY("Technology"),
    PROGRAMMING("Programming"),
    DATABASES("Databases"),
    WEB_DEVELOPMENT("Web Development"),
    MOBILE_DEVELOPMENT("Mobile Development"),
    CLOUD_COMPUTING("Cloud Computing"),
    ARTIFICIAL_INTELLIGENCE("Artificial Intelligence"),
    CYBERSECURITY("Cybersecurity"),
    OTHER("Other");

    private final String displayName;

    QuizCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
