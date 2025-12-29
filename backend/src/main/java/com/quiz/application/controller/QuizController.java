package com.quiz.application.controller;

import com.quiz.application.model.Question;
import com.quiz.application.model.Quiz;
import com.quiz.application.model.QuizAttempt;
import com.quiz.application.model.User;
import com.quiz.application.payload.QuestionRequest;
import com.quiz.application.payload.QuizDto;
import com.quiz.application.payload.QuizRequest;
import com.quiz.application.payload.QuizSubmissionRequest;
import com.quiz.application.service.QuizService;
import com.quiz.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/quizzes")
@CrossOrigin(origins = "http://localhost:3000")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private UserService userService;

    // Get all quizzes
    @GetMapping
    public ResponseEntity<?> getAllQuizzes() {
        try {
            // Return hardcoded quiz data for now to test the frontend
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
            
            Map<String, Object> quiz3 = new HashMap<>();
            quiz3.put("id", 3L);
            quiz3.put("title", "General Science Quiz");
            quiz3.put("description", "Test your understanding of scientific concepts");
            quizList.add(quiz3);
            
            Map<String, Object> quiz4 = new HashMap<>();
            quiz4.put("id", 4L);
            quiz4.put("title", "World History Quiz");
            quiz4.put("description", "Explore historical events and figures");
            quizList.add(quiz4);
            
            Map<String, Object> quiz5 = new HashMap<>();
            quiz5.put("id", 5L);
            quiz5.put("title", "Sports Quiz");
            quiz5.put("description", "Test your sports knowledge");
            quizList.add(quiz5);
            
            return ResponseEntity.ok(quizList);
        } catch (Exception e) {
            System.err.println("Error in getAllQuizzes: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // Test endpoint
    @GetMapping("/test")
    public ResponseEntity<?> testQuizzes() {
        try {
            List<Quiz> quizzes = quizService.getAllQuizzes();
            return ResponseEntity.ok("Found " + quizzes.size() + " quizzes");
        } catch (Exception e) {
            System.err.println("Error in testQuizzes: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // Simple quiz list endpoint
    @GetMapping("/simple")
    public ResponseEntity<?> getSimpleQuizzes() {
        try {
            List<Quiz> quizzes = quizService.getAllQuizzes();
            StringBuilder result = new StringBuilder();
            result.append("Quizzes:\n");
            for (Quiz quiz : quizzes) {
                result.append("- ").append(quiz.getTitle()).append("\n");
            }
            return ResponseEntity.ok(result.toString());
        } catch (Exception e) {
            System.err.println("Error in getSimpleQuizzes: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // Get quiz by ID
    @GetMapping("/{id}")
    public QuizDto getQuizById(@PathVariable Long id) {
        Quiz quiz = quizService.getQuizById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));
        return convertToDto(quiz);
    }

    // Get questions for a quiz
    @GetMapping("/{id}/questions")
    public List<Question> getQuestionsForQuiz(@PathVariable Long id) {
        Quiz quiz = quizService.getQuizById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));
        return quizService.getQuestionsForQuiz(quiz);
    }

    // Create a new quiz (admin only)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public QuizDto createQuiz(@RequestBody QuizRequest quizRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User currentUser = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Quiz quiz = Quiz.builder()
                .title(quizRequest.getTitle())
                .description(quizRequest.getDescription())
                .createdBy(currentUser)
                .build();
                
        Quiz createdQuiz = quizService.createQuiz(quiz);
        return convertToDto(createdQuiz);
    }

    // Update a quiz (admin only)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public QuizDto updateQuiz(@PathVariable Long id, @RequestBody QuizRequest quizRequest) {
        Quiz quiz = quizService.getQuizById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));
        
        quiz.setTitle(quizRequest.getTitle());
        quiz.setDescription(quizRequest.getDescription());
        
        Quiz updatedQuiz = quizService.updateQuiz(quiz);
        return convertToDto(updatedQuiz);
    }

    // Delete a quiz (admin only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.ok().build();
    }

    // Submit quiz answers and calculate score
    @PostMapping("/{id}/submit")
    public QuizAttempt submitQuiz(@PathVariable Long id, @RequestBody QuizSubmissionRequest submissionRequest) {
        Quiz quiz = quizService.getQuizById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User currentUser = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return quizService.submitQuiz(currentUser, quiz, submissionRequest.getAnswers());
    }

    // Get quiz attempts for current user
    @GetMapping("/attempts")
    public List<QuizAttempt> getMyAttempts() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User currentUser = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return quizService.getAttemptsForUser(currentUser);
    }

    // Get quiz attempts for a specific quiz
    @GetMapping("/{id}/attempts")
    @PreAuthorize("hasRole('ADMIN')")
    public List<QuizAttempt> getQuizAttempts(@PathVariable Long id) {
        Quiz quiz = quizService.getQuizById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));
        
        return quizService.getAttemptsForQuiz(quiz);
    }

    // Add a question to a quiz (admin only)
    @PostMapping("/{id}/questions")
    @PreAuthorize("hasRole('ADMIN')")
    public Question addQuestionToQuiz(@PathVariable Long id, @RequestBody QuestionRequest questionRequest) {
        Quiz quiz = quizService.getQuizById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));
        
        Question question = Question.builder()
                .quiz(quiz)
                .questionText(questionRequest.getQuestionText())
                .optionA(questionRequest.getOptionA())
                .optionB(questionRequest.getOptionB())
                .optionC(questionRequest.getOptionC())
                .optionD(questionRequest.getOptionD())
                .correctAnswer(questionRequest.getCorrectAnswer())
                .build();
                
        return quizService.addQuestionToQuiz(question);
    }

    // Update a question (admin only)
    @PutMapping("/questions/{questionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Question updateQuestion(@PathVariable Long questionId, @RequestBody QuestionRequest questionRequest) {
        Question question = quizService.getQuestionById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + questionId));
        
        question.setQuestionText(questionRequest.getQuestionText());
        question.setOptionA(questionRequest.getOptionA());
        question.setOptionB(questionRequest.getOptionB());
        question.setOptionC(questionRequest.getOptionC());
        question.setOptionD(questionRequest.getOptionD());
        question.setCorrectAnswer(questionRequest.getCorrectAnswer());
        
        return quizService.updateQuestion(question);
    }

    // Delete a question (admin only)
    @DeleteMapping("/questions/{questionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {
        quizService.deleteQuestion(questionId);
        return ResponseEntity.ok().build();
    }

    private QuizDto convertToDto(Quiz quiz) {
        QuizDto dto = new QuizDto();
        dto.setId(quiz.getId());
        dto.setTitle(quiz.getTitle());
        dto.setDescription(quiz.getDescription());
        if (quiz.getCreatedBy() != null) {
            dto.setCreatedByUsername(quiz.getCreatedBy().getUsername());
        }
        return dto;
    }
}