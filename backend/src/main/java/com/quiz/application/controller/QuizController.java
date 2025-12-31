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
            List<Quiz> quizzes = quizService.getAllQuizzes();
            return ResponseEntity.ok(quizzes);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching quizzes: " + e.getMessage());
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
                .category(com.quiz.application.model.QuizCategory.OTHER)
                .difficulty(com.quiz.application.model.Difficulty.EASY)
                .timeLimitMinutes(15)
                .status(com.quiz.application.model.QuizStatus.PUBLISHED)
                .visibility(com.quiz.application.model.QuizVisibility.PUBLIC)
                .allowMultipleAttempts(true)
                .randomizeQuestions(false)
                .randomizeOptions(false)
                .negativeMarking(false)
                .passingPercentage(60)
                .maxAttempts(3)
                .createdBy(currentUser)
                .build();
                
        Quiz createdQuiz = quizService.createQuiz(quiz);
        return convertToDto(createdQuiz);
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

    // Add a question to a quiz (admin only)
    @PostMapping("/{id}/questions")
    @PreAuthorize("hasRole('ADMIN')")
    public Question addQuestionToQuiz(@PathVariable Long id, @RequestBody QuestionRequest questionRequest) {
        Quiz quiz = quizService.getQuizById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));
        
        Question question = Question.builder()
                .quiz(quiz)
                .questionText(questionRequest.getQuestionText())
                .questionType(com.quiz.application.model.QuestionType.SINGLE_CORRECT)
                .optionA(questionRequest.getOptionA())
                .optionB(questionRequest.getOptionB())
                .optionC(questionRequest.getOptionC())
                .optionD(questionRequest.getOptionD())
                .correctAnswer(questionRequest.getCorrectAnswer())
                .explanation("")
                .questionOrder(1)
                .marks(1)
                .build();
                
        return quizService.addQuestionToQuiz(question);
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
