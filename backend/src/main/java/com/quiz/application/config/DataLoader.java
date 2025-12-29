package com.quiz.application.config;

import com.quiz.application.model.Question;
import com.quiz.application.model.Quiz;
import com.quiz.application.model.User;
import com.quiz.application.repository.QuestionRepository;
import com.quiz.application.repository.QuizRepository;
import com.quiz.application.repository.UserRepository;
import com.quiz.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

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
                createTechnicalQuiz(adminUser);
                createGeographyQuiz(adminUser);
                createScienceQuiz(adminUser);
                createHistoryQuiz(adminUser);
                createSportsQuiz(adminUser);
                
                System.out.println("Created 5 sample quizzes with multiple questions each");
            }
        }
    }

    private void createTechnicalQuiz(User adminUser) {
        Quiz quiz = Quiz.builder()
                .title("Java Programming Quiz")
                .description("Test your knowledge of Java programming fundamentals")
                .createdBy(adminUser)
                .build();
        
        quiz = quizRepository.save(quiz);
        
        // Java Questions
        Question q1 = Question.builder()
                .quiz(quiz)
                .questionText("Which of the following is not a Java keyword?")
                .optionA("final")
                .optionB("static")
                .optionC("integer")
                .optionD("void")
                .correctAnswer("C")
                .build();
        
        Question q2 = Question.builder()
                .quiz(quiz)
                .questionText("What is the size of an int variable in Java?")
                .optionA("16 bit")
                .optionB("32 bit")
                .optionC("64 bit")
                .optionD("8 bit")
                .correctAnswer("B")
                .build();
        
        Question q3 = Question.builder()
                .quiz(quiz)
                .questionText("Which collection class allows null values and null keys?")
                .optionA("HashMap")
                .optionB("HashTable")
                .optionC("TreeMap")
                .optionD("LinkedHashMap")
                .correctAnswer("A")
                .build();
        
        Question q4 = Question.builder()
                .quiz(quiz)
                .questionText("What is the default value of a boolean variable in Java?")
                .optionA("true")
                .optionB("false")
                .optionC("null")
                .optionD("0")
                .correctAnswer("B")
                .build();
        
        Question q5 = Question.builder()
                .quiz(quiz)
                .questionText("Which method is used to start a thread in Java?")
                .optionA("run()")
                .optionB("start()")
                .optionC("execute()")
                .optionD("init()")
                .correctAnswer("B")
                .build();
        
        questionRepository.saveAll(java.util.List.of(q1, q2, q3, q4, q5));
    }

    private void createGeographyQuiz(User adminUser) {
        Quiz quiz = Quiz.builder()
                .title("World Geography Quiz")
                .description("Test your knowledge of world geography")
                .createdBy(adminUser)
                .build();
        
        quiz = quizRepository.save(quiz);
        
        // Geography Questions
        Question q1 = Question.builder()
                .quiz(quiz)
                .questionText("What is the capital of France?")
                .optionA("London")
                .optionB("Berlin")
                .optionC("Paris")
                .optionD("Madrid")
                .correctAnswer("C")
                .build();
        
        Question q2 = Question.builder()
                .quiz(quiz)
                .questionText("Which is the largest ocean on Earth?")
                .optionA("Atlantic Ocean")
                .optionB("Indian Ocean")
                .optionC("Arctic Ocean")
                .optionD("Pacific Ocean")
                .correctAnswer("D")
                .build();
        
        Question q3 = Question.builder()
                .quiz(quiz)
                .questionText("How many continents are there?")
                .optionA("5")
                .optionB("6")
                .optionC("7")
                .optionD("8")
                .correctAnswer("C")
                .build();
        
        Question q4 = Question.builder()
                .quiz(quiz)
                .questionText("What is the longest river in the world?")
                .optionA("Amazon")
                .optionB("Nile")
                .optionC("Yangtze")
                .optionD("Mississippi")
                .correctAnswer("B")
                .build();
        
        Question q5 = Question.builder()
                .quiz(quiz)
                .questionText("Which country has the largest population?")
                .optionA("India")
                .optionB("United States")
                .optionC("China")
                .optionD("Indonesia")
                .correctAnswer("C")
                .build();
        
        questionRepository.saveAll(java.util.List.of(q1, q2, q3, q4, q5));
    }

    private void createScienceQuiz(User adminUser) {
        Quiz quiz = Quiz.builder()
                .title("General Science Quiz")
                .description("Test your knowledge of general science")
                .createdBy(adminUser)
                .build();
        
        quiz = quizRepository.save(quiz);
        
        // Science Questions
        Question q1 = Question.builder()
                .quiz(quiz)
                .questionText("What is the chemical symbol for gold?")
                .optionA("Go")
                .optionB("Gd")
                .optionC("Au")
                .optionD("Ag")
                .correctAnswer("C")
                .build();
        
        Question q2 = Question.builder()
                .quiz(quiz)
                .questionText("What is the speed of light in vacuum?")
                .optionA("299,792 km/s")
                .optionB("150,000 km/s")
                .optionC("399,792 km/s")
                .optionD("199,792 km/s")
                .correctAnswer("A")
                .build();
        
        Question q3 = Question.builder()
                .quiz(quiz)
                .questionText("What is the largest planet in our solar system?")
                .optionA("Saturn")
                .optionB("Jupiter")
                .optionC("Neptune")
                .optionD("Uranus")
                .correctAnswer("B")
                .build();
        
        Question q4 = Question.builder()
                .quiz(quiz)
                .questionText("What is the powerhouse of the cell?")
                .optionA("Nucleus")
                .optionB("Ribosome")
                .optionC("Mitochondria")
                .optionD("Golgi apparatus")
                .correctAnswer("C")
                .build();
        
        Question q5 = Question.builder()
                .quiz(quiz)
                .questionText("What is H2O commonly known as?")
                .optionA("Hydrogen peroxide")
                .optionB("Water")
                .optionC("Carbon dioxide")
                .optionD("Oxygen")
                .correctAnswer("B")
                .build();
        
        questionRepository.saveAll(java.util.List.of(q1, q2, q3, q4, q5));
    }

    private void createHistoryQuiz(User adminUser) {
        Quiz quiz = Quiz.builder()
                .title("World History Quiz")
                .description("Test your knowledge of world history")
                .createdBy(adminUser)
                .build();
        
        quiz = quizRepository.save(quiz);
        
        // History Questions
        Question q1 = Question.builder()
                .quiz(quiz)
                .questionText("In which year did World War II end?")
                .optionA("1943")
                .optionB("1944")
                .optionC("1945")
                .optionD("1946")
                .correctAnswer("C")
                .build();
        
        Question q2 = Question.builder()
                .quiz(quiz)
                .questionText("Who was the first President of the United States?")
                .optionA("Thomas Jefferson")
                .optionB("George Washington")
                .optionC("John Adams")
                .optionD("Benjamin Franklin")
                .correctAnswer("B")
                .build();
        
        Question q3 = Question.builder()
                .quiz(quiz)
                .questionText("Which ancient wonder of the world still stands today?")
                .optionA("Colossus of Rhodes")
                .optionB("Hanging Gardens of Babylon")
                .optionC("Great Pyramid of Giza")
                .optionD("Lighthouse of Alexandria")
                .correctAnswer("C")
                .build();
        
        Question q4 = Question.builder()
                .quiz(quiz)
                .questionText("In which year did Christopher Columbus reach the Americas?")
                .optionA("1490")
                .optionB("1491")
                .optionC("1492")
                .optionD("1493")
                .correctAnswer("C")
                .build();
        
        Question q5 = Question.builder()
                .quiz(quiz)
                .questionText("Which empire was known as 'The Empire on which the sun never sets'?")
                .optionA("Roman Empire")
                .optionB("British Empire")
                .optionC("Ottoman Empire")
                .optionD("Mongol Empire")
                .correctAnswer("B")
                .build();
        
        questionRepository.saveAll(java.util.List.of(q1, q2, q3, q4, q5));
    }

    private void createSportsQuiz(User adminUser) {
        Quiz quiz = Quiz.builder()
                .title("Sports Quiz")
                .description("Test your knowledge of various sports")
                .createdBy(adminUser)
                .build();
        
        quiz = quizRepository.save(quiz);
        
        // Sports Questions
        Question q1 = Question.builder()
                .quiz(quiz)
                .questionText("How many players are on a standard soccer team on the field?")
                .optionA("9")
                .optionB("10")
                .optionC("11")
                .optionD("12")
                .correctAnswer("C")
                .build();
        
        Question q2 = Question.builder()
                .quiz(quiz)
                .questionText("In which sport would you perform a slam dunk?")
                .optionA("Tennis")
                .optionB("Basketball")
                .optionC("Volleyball")
                .optionD("Baseball")
                .correctAnswer("B")
                .build();
        
        Question q3 = Question.builder()
                .quiz(quiz)
                .questionText("How often are the Olympic Games held?")
                .optionA("Every 2 years")
                .optionB("Every 3 years")
                .optionC("Every 4 years")
                .optionD("Every 5 years")
                .correctAnswer("C")
                .build();
        
        Question q4 = Question.builder()
                .quiz(quiz)
                .questionText("Which country has won the most FIFA World Cups?")
                .optionA("Germany")
                .optionB("Argentina")
                .optionC("Brazil")
                .optionD("Italy")
                .correctAnswer("C")
                .build();
        
        Question q5 = Question.builder()
                .quiz(quiz)
                .questionText("In which sport would you find a 'grand slam'?")
                .optionA("Golf")
                .optionB("Tennis")
                .optionC("Cricket")
                .optionD("Swimming")
                .correctAnswer("B")
                .build();
        
        questionRepository.saveAll(java.util.List.of(q1, q2, q3, q4, q5));
    }
}