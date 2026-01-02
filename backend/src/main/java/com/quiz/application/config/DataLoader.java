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
            System.out.println("No quizzes found, creating sample quizzes...");
            User adminUser = userService.getUserByUsername("admin").orElse(null);
            
            if (adminUser != null) {
                createSampleQuiz(adminUser);
                createReactQuiz(adminUser);
                createDatabaseQuiz(adminUser);
                createPythonQuiz(adminUser);
                createWebDevQuiz(adminUser);
                System.out.println("Created 5 sample quizzes with questions");
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
    
    private void createReactQuiz(User adminUser) {
        Quiz quiz = Quiz.builder()
                .title("React Fundamentals Quiz")
                .description("Test your knowledge of React.js concepts and hooks")
                .category(QuizCategory.PROGRAMMING)
                .difficulty(Difficulty.MEDIUM)
                .timeLimitMinutes(20)
                .status(QuizStatus.PUBLISHED)
                .visibility(QuizVisibility.PUBLIC)
                .allowMultipleAttempts(true)
                .randomizeQuestions(false)
                .randomizeOptions(false)
                .negativeMarking(false)
                .passingPercentage(70)
                .maxAttempts(3)
                .createdBy(adminUser)
                .build();
        
        quiz = quizRepository.save(quiz);
        
        List<Question> questions = new ArrayList<>();
        
        questions.add(Question.builder()
                .quiz(quiz)
                .questionText("What hook is used to manage state in functional components?")
                .questionType(QuestionType.SINGLE_CORRECT)
                .optionA("useEffect")
                .optionB("useState")
                .optionC("useContext")
                .optionD("useReducer")
                .correctAnswer("B")
                .explanation("useState is the primary hook for managing state in functional React components.")
                .questionOrder(1)
                .marks(1)
                .build());
        
        questions.add(Question.builder()
                .quiz(quiz)
                .questionText("React components must return a single parent element.")
                .questionType(QuestionType.TRUE_FALSE)
                .optionA("True")
                .optionB("False")
                .correctAnswer("A")
                .explanation("React components must return a single root element, though Fragments can be used to group multiple elements without adding extra DOM nodes.")
                .questionOrder(2)
                .marks(1)
                .build());
        
        questions.add(Question.builder()
                .quiz(quiz)
                .questionText("Which of the following are React hooks? (Select all that apply)")
                .questionType(QuestionType.MULTIPLE_CORRECT)
                .optionA("useState")
                .optionB("useEffect")
                .optionC("useComponent")
                .optionD("useContext")
                .correctAnswers("A,B,D")
                .explanation("useState, useEffect, and useContext are valid React hooks. useComponent does not exist.")
                .questionOrder(3)
                .marks(2)
                .build());
        
        questions.add(Question.builder()
                .quiz(quiz)
                .questionText("What is the purpose of useEffect hook?")
                .questionType(QuestionType.SINGLE_CORRECT)
                .optionA("To manage state")
                .optionB("To handle side effects")
                .optionC("To create context")
                .optionD("To optimize performance")
                .correctAnswer("B")
                .explanation("useEffect is used to handle side effects in functional components, such as API calls, subscriptions, or DOM manipulations.")
                .questionOrder(4)
                .marks(1)
                .build());
        
        questionRepository.saveAll(questions);
    }
    
    private void createDatabaseQuiz(User adminUser) {
        Quiz quiz = Quiz.builder()
                .title("Database Fundamentals Quiz")
                .description("Test your knowledge of database concepts and SQL")
                .category(QuizCategory.DATABASES)
                .difficulty(Difficulty.MEDIUM)
                .timeLimitMinutes(25)
                .status(QuizStatus.PUBLISHED)
                .visibility(QuizVisibility.PUBLIC)
                .allowMultipleAttempts(true)
                .randomizeQuestions(false)
                .randomizeOptions(false)
                .negativeMarking(false)
                .passingPercentage(65)
                .maxAttempts(3)
                .createdBy(adminUser)
                .build();
        
        quiz = quizRepository.save(quiz);
        
        List<Question> questions = new ArrayList<>();
        
        questions.add(Question.builder()
                .quiz(quiz)
                .questionText("What does SQL stand for?")
                .questionType(QuestionType.SINGLE_CORRECT)
                .optionA("Structured Query Language")
                .optionB("Simple Query Language")
                .optionC("Standard Query Language")
                .optionD("System Query Language")
                .correctAnswer("A")
                .explanation("SQL stands for Structured Query Language and is used for managing relational databases.")
                .questionOrder(1)
                .marks(1)
                .build());
        
        questions.add(Question.builder()
                .quiz(quiz)
                .questionText("Primary keys cannot contain NULL values.")
                .questionType(QuestionType.TRUE_FALSE)
                .optionA("True")
                .optionB("False")
                .correctAnswer("A")
                .explanation("Primary keys must contain unique, non-NULL values to uniquely identify each record in a table.")
                .questionOrder(2)
                .marks(1)
                .build());
        
        questions.add(Question.builder()
                .quiz(quiz)
                .questionText("Which of the following are SQL commands? (Select all that apply)")
                .questionType(QuestionType.MULTIPLE_CORRECT)
                .optionA("SELECT")
                .optionB("INSERT")
                .optionC("UPDATE")
                .optionD("GET")
                .correctAnswers("A,B,C")
                .explanation("SELECT, INSERT, and UPDATE are valid SQL commands. GET is not a standard SQL command.")
                .questionOrder(3)
                .marks(2)
                .build());
        
        questions.add(Question.builder()
                .quiz(quiz)
                .questionText("What is a foreign key?")
                .questionType(QuestionType.SINGLE_CORRECT)
                .optionA("A key that references another table's primary key")
                .optionB("A unique identifier for a record")
                .optionC("A key used for encryption")
                .optionD("A backup key")
                .correctAnswer("A")
                .explanation("A foreign key is a field that uniquely identifies a row in another table by referencing the primary key of that table.")
                .questionOrder(4)
                .marks(1)
                .build());
        
        questions.add(Question.builder()
                .quiz(quiz)
                .questionText("Which JOIN returns all records from the left table and matching records from the right table?")
                .questionType(QuestionType.SINGLE_CORRECT)
                .optionA("INNER JOIN")
                .optionB("LEFT JOIN")
                .optionC("RIGHT JOIN")
                .optionD("FULL OUTER JOIN")
                .correctAnswer("B")
                .explanation("LEFT JOIN returns all records from the left table and the matched records from the right table.")
                .questionOrder(5)
                .marks(1)
                .build());
        
        questionRepository.saveAll(questions);
    }
    
    private void createPythonQuiz(User adminUser) {
        Quiz quiz = Quiz.builder()
                .title("Python Programming Quiz")
                .description("Test your knowledge of Python programming basics")
                .category(QuizCategory.PROGRAMMING)
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
                .questionText("Which keyword is used to define a function in Python?")
                .questionType(QuestionType.SINGLE_CORRECT)
                .optionA("function")
                .optionB("def")
                .optionC("func")
                .optionD("define")
                .correctAnswer("B")
                .explanation("The 'def' keyword is used to define a function in Python.")
                .questionOrder(1)
                .marks(1)
                .build());
        
        questions.add(Question.builder()
                .quiz(quiz)
                .questionText("Python uses indentation to define code blocks.")
                .questionType(QuestionType.TRUE_FALSE)
                .optionA("True")
                .optionB("False")
                .correctAnswer("A")
                .explanation("Python uses whitespace indentation to define code blocks, unlike other languages that use braces.")
                .questionOrder(2)
                .marks(1)
                .build());
        
        questions.add(Question.builder()
                .quiz(quiz)
                .questionText("Which of the following are Python data types? (Select all that apply)")
                .questionType(QuestionType.MULTIPLE_CORRECT)
                .optionA("list")
                .optionB("tuple")
                .optionC("dictionary")
                .optionD("array")
                .correctAnswers("A,B,C")
                .explanation("list, tuple, and dictionary are built-in Python data types. array is not a built-in type (though available through modules).")
                .questionOrder(3)
                .marks(2)
                .build());
        
        questions.add(Question.builder()
                .quiz(quiz)
                .questionText("What is the output of print(2 ** 3)?")
                .questionType(QuestionType.SINGLE_CORRECT)
                .optionA("6")
                .optionB("8")
                .optionC("9")
                .optionD("5")
                .correctAnswer("B")
                .explanation("The ** operator in Python is used for exponentiation, so 2 ** 3 equals 8.")
                .questionOrder(4)
                .marks(1)
                .build());
        
        questionRepository.saveAll(questions);
    }
    
    private void createWebDevQuiz(User adminUser) {
        Quiz quiz = Quiz.builder()
                .title("Web Development Quiz")
                .description("Test your knowledge of HTML, CSS, and web technologies")
                .category(QuizCategory.WEB_DEVELOPMENT)
                .difficulty(Difficulty.EASY)
                .timeLimitMinutes(20)
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
                .questionText("What does HTML stand for?")
                .questionType(QuestionType.SINGLE_CORRECT)
                .optionA("Hyper Text Markup Language")
                .optionB("High Tech Modern Language")
                .optionC("Home Tool Markup Language")
                .optionD("Hyperlinks and Text Markup Language")
                .correctAnswer("A")
                .explanation("HTML stands for Hyper Text Markup Language and is used for creating web pages.")
                .questionOrder(1)
                .marks(1)
                .build());
        
        questions.add(Question.builder()
                .quiz(quiz)
                .questionText("CSS is used to style web pages.")
                .questionType(QuestionType.TRUE_FALSE)
                .optionA("True")
                .optionB("False")
                .correctAnswer("A")
                .explanation("CSS (Cascading Style Sheets) is indeed used to style and layout web pages.")
                .questionOrder(2)
                .marks(1)
                .build());
        
        questions.add(Question.builder()
                .quiz(quiz)
                .questionText("Which of the following are HTML5 semantic elements? (Select all that apply)")
                .questionType(QuestionType.MULTIPLE_CORRECT)
                .optionA("<header>")
                .optionB("<nav>")
                .optionC("<div>")
                .optionD("<section>")
                .correctAnswers("A,B,D")
                .explanation("<header>, <nav>, and <section> are HTML5 semantic elements. <div> is not specifically semantic.")
                .questionOrder(3)
                .marks(2)
                .build());
        
        questions.add(Question.builder()
                .quiz(quiz)
                .questionText("Which CSS property is used to change the text color?")
                .questionType(QuestionType.SINGLE_CORRECT)
                .optionA("text-color")
                .optionB("color")
                .optionC("font-color")
                .optionD("text-style")
                .correctAnswer("B")
                .explanation("The 'color' property in CSS is used to change the text color of an element.")
                .questionOrder(4)
                .marks(1)
                .build());
        
        questions.add(Question.builder()
                .quiz(quiz)
                .questionText("What is the purpose of JavaScript in web development?")
                .questionType(QuestionType.SINGLE_CORRECT)
                .optionA("To style web pages")
                .optionB("To create structure")
                .optionC("To add interactivity")
                .optionD("To store data")
                .correctAnswer("C")
                .explanation("JavaScript is primarily used to add interactivity and dynamic behavior to web pages.")
                .questionOrder(5)
                .marks(1)
                .build());
        
        questionRepository.saveAll(questions);
    }
}
