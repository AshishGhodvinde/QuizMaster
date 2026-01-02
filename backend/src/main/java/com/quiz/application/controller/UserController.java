package com.quiz.application.controller;

import com.quiz.application.model.User;
import com.quiz.application.model.QuizAttempt;
import com.quiz.application.service.UserService;
import com.quiz.application.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private QuizService quizService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/me")
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }
    
    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public AdminStats getAdminStats() {
        List<User> users = userService.getAllUsers();
        List<QuizAttempt> attempts = quizService.getAllAttempts();
        
        return new AdminStats(
            users.size(),
            attempts.size(),
            quizService.getAllQuizzes().size()
        );
    }
    
    // Inner class for stats response
    public static class AdminStats {
        private int totalUsers;
        private int totalAttempts;
        private int totalQuizzes;
        
        public AdminStats(int totalUsers, int totalAttempts, int totalQuizzes) {
            this.totalUsers = totalUsers;
            this.totalAttempts = totalAttempts;
            this.totalQuizzes = totalQuizzes;
        }
        
        public int getTotalUsers() { return totalUsers; }
        public int getTotalAttempts() { return totalAttempts; }
        public int getTotalQuizzes() { return totalQuizzes; }
    }
}