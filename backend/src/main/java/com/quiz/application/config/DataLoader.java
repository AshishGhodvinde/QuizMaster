package com.quiz.application.config;

import com.quiz.application.model.*;
import com.quiz.application.repository.QuestionRepository;
import com.quiz.application.repository.QuizRepository;
import com.quiz.application.repository.UserRepository;
import com.quiz.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create admin user if not exists
        if (!userService.existsByUsername("admin")) {
            userService.createUser("admin", "admin@example.com", "admin123", "ADMIN");
            System.out.println("Admin user created with username: admin and password: admin123");
        }

        // Create sample user if not exists
        if (!userService.existsByUsername("user")) {
            userService.createUser("user", "user@example.com", "user123", "USER");
            System.out.println("Sample user created with username: user and password: user123");
        }
        
        // Create sample quizzes if not exists
        if (quizRepository.count() == 0) {
            User adminUser = userService.getUserByUsername("admin").orElse(null);
            
            if (adminUser != null) {
                createSampleQuiz(adminUser);
                System.out.println("Created sample quiz with questions");
            }
        }
        
        System.out.println("DataLoader completed successfully!");
    }
    
    private void createSampleQuiz(User adminUser) {
        Quiz quiz = Quiz.builder()
                .title("Java Fundamentals Quiz")
                .description("Test your knowledge of basic Java programming concepts")
                .category(QuizCategory.JAVA)
                .difficulty(Difficulty.EASY)
                .timeLimitMinutes(15)
                .status(QuizStatus.PUBLISHED)
                .visibility(QuizVisibility.PUBLIC)
                .allowMultipleAttempts(true)
                .randomizeQuestions(false)
                .randomizeOptions(false)
                .negativeMarking(false)
                .passingPercentage(60)
                .maxAttempts(3)
                .createdBy(adminUser)
                .build();
        
        quiz = quizRepository.save(quiz);
        
        List<Question> questions = new ArrayList<>();
        
        questions.add(Question.builder()
                .quiz(quiz)
                .questionText("What is the size of an int variable in Java?")
                .questionType(QuestionType.SINGLE_CORRECT)
                .optionA("16 bit")
                .optionB("32 bit")
                .optionC("64 bit")
                .optionD("8 bit")
                .correctAnswer("B")
                .explanation("In Java, an int variable is 32 bits or 4 bytes.")
                .questionOrder(1)
                .marks(1)
                .build());
        
        questions.add(Question.builder()
                .quiz(quiz)
                .questionText("Which keyword is used to declare a constant in Java?")
                .questionType(QuestionType.SINGLE_CORRECT)
                .optionA("static")
                .optionB("final")
                .optionC("const")
                .optionD("immutable")
                .correctAnswer("B")
                .explanation("The 'final' keyword is used to declare constants in Java.")
                .questionOrder(2)
                .marks(1)
                .build());
        
        questions.add(Question.builder()
                .quiz(quiz)
                .questionText("Java is a platform-independent language.")
                .questionType(QuestionType.TRUE_FALSE)
                .optionA("True")
                .optionB("False")
                .correctAnswer("A")
                .explanation("Java is platform-independent due to its 'write once, run anywhere' capability through the JVM.")
                .questionOrder(3)
                .marks(1)
                .build());
        
        questions.add(Question.builder()
                .quiz(quiz)
                .questionText("Which of the following are valid Java data types? (Select all that apply)")
                .questionType(QuestionType.MULTIPLE_CORRECT)
                .optionA("int")
                .optionB("float")
                .optionC("boolean")
                .optionD("string")
                .correctAnswers("A,B,C")
                .explanation("int, float, and boolean are valid Java primitive types. string should be 'String' (capitalized) for the class type.")
                .questionOrder(4)
                .marks(2)
                .build());
        
        questions.add(Question.builder()
                .quiz(quiz)
                .questionText("What is the default value of a boolean variable in Java?")
                .questionType(QuestionType.SINGLE_CORRECT)
                .optionA("true")
                .optionB("false")
                .optionC("null")
                .optionD("0")
                .correctAnswer("B")
                .explanation("The default value of a boolean variable in Java is false.")
                .questionOrder(5)
                .marks(1)
                .build());
        
        questionRepository.saveAll(questions);
    }
}
