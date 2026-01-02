package com.quiz.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.application.model.*;
import com.quiz.application.repository.QuestionRepository;
import com.quiz.application.repository.QuizAttemptRepository;
import com.quiz.application.repository.QuizRepository;
import com.quiz.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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

    @Autowired
    private ObjectMapper objectMapper;

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

    public QuizAttempt submitQuiz(User user, Quiz quiz, Map<Long, Object> answers) {
        List<Question> questions = questionRepository.findByQuiz(quiz);
        
        int correctAnswers = 0;
        int incorrectAnswers = 0;
        int totalMarks = questions.stream().mapToInt(Question::getMarks).sum();
        double obtainedMarks = 0.0;
        double negativeMarks = 0.0;
        
        for (Question question : questions) {
            Object userAnswer = answers.get(question.getId());
            boolean isCorrect = false;
            
            if (question.getQuestionType() == QuestionType.SINGLE_CORRECT) {
                isCorrect = userAnswer != null && userAnswer.toString().equals(question.getCorrectAnswer());
            } else if (question.getQuestionType() == QuestionType.MULTIPLE_CORRECT) {
                if (userAnswer instanceof List && question.getCorrectAnswers() != null) {
                    List<String> userAnswersList = (List<String>) userAnswer;
                    List<String> correctAnswersList = Arrays.asList(question.getCorrectAnswers().split(","));
                    isCorrect = userAnswersList.containsAll(correctAnswersList) && correctAnswersList.containsAll(userAnswersList);
                }
            } else if (question.getQuestionType() == QuestionType.TRUE_FALSE) {
                isCorrect = userAnswer != null && userAnswer.toString().equalsIgnoreCase(question.getCorrectAnswer());
            }
            
            if (isCorrect) {
                correctAnswers++;
                obtainedMarks += question.getMarks();
            } else if (userAnswer != null) {
                incorrectAnswers++;
                if (quiz.getNegativeMarking() != null && quiz.getNegativeMarking()) {
                    negativeMarks += question.getMarks() * (quiz.getNegativeMarks() != null ? quiz.getNegativeMarks() : 0.25);
                }
            }
        }
        
        obtainedMarks -= negativeMarks;
        
        QuizAttempt attempt = QuizAttempt.builder()
                .user(user)
                .quiz(quiz)
                .totalQuestions(questions.size())
                .correctAnswers(correctAnswers)
                .incorrectAnswers(incorrectAnswers)
                .totalMarks(totalMarks)
                .obtainedMarks(Math.max(0, obtainedMarks))
                .negativeMarks(negativeMarks)
                .startedAt(LocalDateTime.now())
                .submittedAt(LocalDateTime.now())
                .isCompleted(true)
                .build();
        
        // Store answers as JSON
        try {
            attempt.setAnswersData(objectMapper.writeValueAsString(answers));
        } catch (JsonProcessingException e) {
            System.err.println("Error serializing answers: " + e.getMessage());
        }
                
        return quizAttemptRepository.save(attempt);
    }

    public List<QuizAttempt> getAttemptsForUser(User user) {
        return quizAttemptRepository.findByUser(user);
    }

    public List<QuizAttempt> getAttemptsForQuiz(Quiz quiz) {
        return quizAttemptRepository.findByQuiz(quiz);
    }

    public List<QuizAttempt> getAllAttempts() {
        return quizAttemptRepository.findAll();
    }

    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    public int getQuestionCountByQuizId(Long quizId) {
        return questionRepository.countByQuizId(quizId);
    }
}