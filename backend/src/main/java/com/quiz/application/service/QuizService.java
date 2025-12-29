package com.quiz.application.service;

import com.quiz.application.model.Question;
import com.quiz.application.model.Quiz;
import com.quiz.application.model.QuizAttempt;
import com.quiz.application.model.User;
import com.quiz.application.repository.QuestionRepository;
import com.quiz.application.repository.QuizAttemptRepository;
import com.quiz.application.repository.QuizRepository;
import com.quiz.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Optional<Quiz> getQuizById(Long id) {
        return quizRepository.findById(id);
    }

    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Quiz updateQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }

    public List<Question> getQuestionsForQuiz(Quiz quiz) {
        return questionRepository.findByQuiz(quiz);
    }

    public Question addQuestionToQuiz(Question question) {
        return questionRepository.save(question);
    }

    public Question updateQuestion(Question question) {
        return questionRepository.save(question);
    }

    public void deleteQuestion(Long questionId) {
        questionRepository.deleteById(questionId);
    }

    public QuizAttempt submitQuiz(User user, Quiz quiz, List<String> answers) {
        List<Question> questions = questionRepository.findByQuiz(quiz);
        
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (i < answers.size() && answers.get(i).equals(questions.get(i).getCorrectAnswer())) {
                score++;
            }
        }
        
        QuizAttempt attempt = QuizAttempt.builder()
                .user(user)
                .quiz(quiz)
                .score(score)
                .totalQuestions(questions.size())
                .build();
                
        return quizAttemptRepository.save(attempt);
    }

    public List<QuizAttempt> getAttemptsForUser(User user) {
        return quizAttemptRepository.findByUser(user);
    }

    public List<QuizAttempt> getAttemptsForQuiz(Quiz quiz) {
        return quizAttemptRepository.findByQuiz(quiz);
    }

    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }
}