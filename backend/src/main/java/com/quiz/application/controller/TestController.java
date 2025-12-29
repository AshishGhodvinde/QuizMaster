package com.quiz.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "http://localhost:3000")
public class TestController {

    @GetMapping("/quizzes")
    public ResponseEntity<?> getQuizzes() {
        try {
            List<Map<String, Object>> quizList = new ArrayList<>();
            
            Map<String, Object> quiz1 = new HashMap<>();
            quiz1.put("id", 1L);
            quiz1.put("title", "Java Programming Quiz");
            quiz1.put("description", "Test your knowledge of Java programming concepts");
            quizList.add(quiz1);
            
            Map<String, Object> quiz2 = new HashMap<>();
            quiz2.put("id", 2L);
            quiz2.put("title", "World Geography Quiz");
            quiz2.put("description", "Challenge your geography knowledge");
            quizList.add(quiz2);
            
            return ResponseEntity.ok(quizList);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
