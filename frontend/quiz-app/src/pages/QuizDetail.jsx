import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import quizService from '../services/quizService';
import { FiClock, FiBarChart2, FiCheck, FiX } from 'react-icons/fi';

const QuizDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [quiz, setQuiz] = useState(null);
  const [questions, setQuestions] = useState([]);
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [selectedAnswers, setSelectedAnswers] = useState({});
  const [showResults, setShowResults] = useState(false);
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(true);
  const [timeLeft, setTimeLeft] = useState(900); // 15 minutes in seconds

  useEffect(() => {
    const fetchQuizData = async () => {
      try {
        const [quizData, questionsData] = await Promise.all([
          quizService.getQuizById(id),
          quizService.getQuestionsForQuiz(id)
        ]);
        
        setQuiz(quizData);
        setQuestions(questionsData);
        setLoading(false);
      } catch (error) {
        console.error('Error fetching quiz data:', error);
        setLoading(false);
      }
    };
    
    fetchQuizData();
  }, [id]);

  // Timer effect
  useEffect(() => {
    if (showResults || questions.length === 0) return;
    
    const timer = setInterval(() => {
      setTimeLeft(prev => {
        if (prev <= 1) {
          handleSubmit();
          return 0;
        }
        return prev - 1;
      });
    }, 1000);
    
    return () => clearInterval(timer);
  }, [showResults, questions.length]);

  const handleAnswerSelect = (questionIndex, answer) => {
    setSelectedAnswers(prev => ({
      ...prev,
      [questionIndex]: answer
    }));
  };

  const handleNextQuestion = () => {
    if (currentQuestionIndex < questions.length - 1) {
      setCurrentQuestionIndex(prev => prev + 1);
    }
  };

  const handlePrevQuestion = () => {
    if (currentQuestionIndex > 0) {
      setCurrentQuestionIndex(prev => prev - 1);
    }
  };

  const handleSubmit = async () => {
    try {
      // Convert selected answers to array format
      const answersArray = Array(questions.length).fill('');
      Object.keys(selectedAnswers).forEach(index => {
        answersArray[index] = selectedAnswers[index];
      });
      
      const resultData = await quizService.submitQuiz(id, answersArray);
      setResult(resultData);
      setShowResults(true);
    } catch (error) {
      console.error('Error submitting quiz:', error);
    }
  };

  const formatTime = (seconds) => {
    const mins = Math.floor(seconds / 60);
    const secs = seconds % 60;
    return `${mins}:${secs < 10 ? '0' : ''}${secs}`;
  };

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-blue-500"></div>
      </div>
    );
  }

  if (showResults && result) {
    return (
      <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="card p-8 text-center">
          <div className="mx-auto flex items-center justify-center h-16 w-16 rounded-full bg-green-100 mb-6">
            <FiCheck className="h-8 w-8 text-green-600" />
          </div>
          
          <h2 className="text-3xl font-bold text-gray-900 mb-2">Quiz Completed!</h2>
          <p className="text-gray-600 mb-8">Here are your results</p>
          
          <div className="bg-gradient-to-r from-blue-50 to-indigo-50 rounded-2xl p-8 mb-8">
            <div className="text-6xl font-bold text-blue-600 mb-2">
              {Math.round(result.score / result.totalQuestions * 100)}%
            </div>
            <p className="text-gray-600 mb-6">
              You scored {result.score} out of {result.totalQuestions} questions correctly
            </p>
            
            <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mt-8">
              <div className="p-4 bg-white rounded-lg shadow">
                <div className="text-2xl font-bold text-gray-900">{result.score}</div>
                <div className="text-gray-600">Correct Answers</div>
              </div>
              <div className="p-4 bg-white rounded-lg shadow">
                <div className="text-2xl font-bold text-gray-900">{result.totalQuestions - result.score}</div>
                <div className="text-gray-600">Incorrect Answers</div>
              </div>
              <div className="p-4 bg-white rounded-lg shadow">
                <div className="text-2xl font-bold text-gray-900">
                  {formatTime(900 - timeLeft)}
                </div>
                <div className="text-gray-600">Time Taken</div>
              </div>
            </div>
          </div>
          
          <div className="flex flex-col sm:flex-row justify-center gap-4">
            <button
              onClick={() => navigate('/quizzes')}
              className="btn-primary px-6 py-3"
            >
              Browse More Quizzes
            </button>
            <button
              onClick={() => navigate('/dashboard')}
              className="btn-secondary px-6 py-3"
            >
              Go to Dashboard
            </button>
          </div>
        </div>
      </div>
    );
  }

  if (!quiz || questions.length === 0) {
    return (
      <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="card p-8 text-center">
          <div className="mx-auto flex items-center justify-center h-16 w-16 rounded-full bg-red-100 mb-6">
            <FiX className="h-8 w-8 text-red-600" />
          </div>
          <h2 className="text-2xl font-bold text-gray-900 mb-2">Quiz Not Found</h2>
          <p className="text-gray-600 mb-6">The quiz you're looking for doesn't exist or has been removed.</p>
          <button
            onClick={() => navigate('/quizzes')}
            className="btn-primary"
          >
            Browse Quizzes
          </button>
        </div>
      </div>
    );
  }

  const currentQuestion = questions[currentQuestionIndex];
  const progress = ((currentQuestionIndex + 1) / questions.length) * 100;

  return (
    <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      {/* Quiz Header */}
      <div className="card p-6 mb-6">
        <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-4">
          <div>
            <h1 className="text-2xl font-bold text-gray-900">{quiz.title}</h1>
            <p className="text-gray-600">{quiz.description}</p>
          </div>
          <div className="flex items-center bg-red-50 text-red-800 px-4 py-2 rounded-lg">
            <FiClock className="mr-2" />
            <span className="font-medium">{formatTime(timeLeft)}</span>
          </div>
        </div>
        
        {/* Progress Bar */}
        <div className="mt-6">
          <div className="flex justify-between text-sm text-gray-600 mb-1">
            <span>Question {currentQuestionIndex + 1} of {questions.length}</span>
            <span>{Math.round(progress)}% Complete</span>
          </div>
          <div className="w-full bg-gray-200 rounded-full h-2">
            <div 
              className="bg-blue-600 h-2 rounded-full transition-all duration-300" 
              style={{ width: `${progress}%` }}
            ></div>
          </div>
        </div>
      </div>

      {/* Question Card */}
      <div className="card p-6 mb-6">
        <h2 className="text-xl font-semibold text-gray-900 mb-6">
          {currentQuestion.questionText}
        </h2>
        
        <div className="space-y-4">
          {['A', 'B', 'C', 'D'].map((option) => {
            const optionText = currentQuestion[`option${option}`];
            const isSelected = selectedAnswers[currentQuestionIndex] === option;
            
            return (
              <button
                key={option}
                onClick={() => handleAnswerSelect(currentQuestionIndex, option)}
                className={`w-full text-left p-4 rounded-lg border-2 transition-all ${
                  isSelected
                    ? 'border-blue-500 bg-blue-50'
                    : 'border-gray-200 hover:border-gray-300'
                }`}
              >
                <div className="flex items-center">
                  <div className={`flex-shrink-0 h-6 w-6 rounded-full flex items-center justify-center mr-3 ${
                    isSelected ? 'bg-blue-500 text-white' : 'bg-gray-200 text-gray-700'
                  }`}>
                    {option}
                  </div>
                  <span className="text-gray-900">{optionText}</span>
                </div>
              </button>
            );
          })}
        </div>
      </div>

      {/* Navigation */}
      <div className="flex flex-col sm:flex-row justify-between gap-4">
        <button
          onClick={handlePrevQuestion}
          disabled={currentQuestionIndex === 0}
          className={`px-6 py-3 rounded-md font-medium ${
            currentQuestionIndex === 0
              ? 'bg-gray-200 text-gray-500 cursor-not-allowed'
              : 'bg-gray-200 text-gray-700 hover:bg-gray-300'
          }`}
        >
          Previous
        </button>
        
        {currentQuestionIndex < questions.length - 1 ? (
          <button
            onClick={handleNextQuestion}
            className="btn-primary px-6 py-3"
          >
            Next Question
          </button>
        ) : (
          <button
            onClick={handleSubmit}
            disabled={!selectedAnswers[currentQuestionIndex]}
            className={`px-6 py-3 rounded-md font-medium ${
              !selectedAnswers[currentQuestionIndex]
                ? 'bg-gray-200 text-gray-500 cursor-not-allowed'
                : 'bg-green-600 text-white hover:bg-green-700'
            }`}
          >
            Submit Quiz
          </button>
        )}
      </div>
    </div>
  );
};

export default QuizDetail;