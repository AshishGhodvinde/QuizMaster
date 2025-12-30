package com.quiz.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/working")
@CrossOrigin(origins = "http://localhost:3000")
public class WorkingQuizController {

    @GetMapping("/quizzes")
    public ResponseEntity<?> getAllQuizzes() {
        try {
            List<Map<String, Object>> quizList = new ArrayList<>();
            
            Map<String, Object> quiz1 = new HashMap<>();
            quiz1.put("id", 1L);
            quiz1.put("title", "Java Programming Quiz");
            quiz1.put("description", "Test your knowledge of Java programming concepts");
            quiz1.put("createdBy", "admin");
            quizList.add(quiz1);
            
            Map<String, Object> quiz2 = new HashMap<>();
            quiz2.put("id", 2L);
            quiz2.put("title", "World Geography Quiz");
            quiz2.put("description", "Challenge your geography knowledge");
            quiz2.put("createdBy", "admin");
            quizList.add(quiz2);
            
            Map<String, Object> quiz3 = new HashMap<>();
            quiz3.put("id", 3L);
            quiz3.put("title", "General Science Quiz");
            quiz3.put("description", "Test your understanding of scientific concepts");
            quiz3.put("createdBy", "admin");
            quizList.add(quiz3);
            
            Map<String, Object> quiz4 = new HashMap<>();
            quiz4.put("id", 4L);
            quiz4.put("title", "World History Quiz");
            quiz4.put("description", "Explore historical events and figures");
            quiz4.put("createdBy", "admin");
            quizList.add(quiz4);
            
            Map<String, Object> quiz5 = new HashMap<>();
            quiz5.put("id", 5L);
            quiz5.put("title", "Sports Quiz");
            quiz5.put("description", "Test your sports knowledge");
            quiz5.put("createdBy", "admin");
            quizList.add(quiz5);
            
            return ResponseEntity.ok(quizList);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/quizzes")
    public ResponseEntity<?> createQuiz(@RequestBody Map<String, Object> quizRequest) {
        try {
            Map<String, Object> newQuiz = new HashMap<>();
            newQuiz.put("id", System.currentTimeMillis());
            newQuiz.put("title", quizRequest.get("title"));
            newQuiz.put("description", quizRequest.get("description"));
            newQuiz.put("createdBy", "admin");
            
            return ResponseEntity.ok(newQuiz);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
