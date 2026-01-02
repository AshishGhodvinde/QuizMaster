import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { FiPlus, FiTrash2, FiSave, FiArrowLeft } from 'react-icons/fi';
// import quizService from '../services/quizService';

// Temporary inline quizService until file system issue is resolved
const quizService = {
  createQuiz: async (quizData) => {
    try {
      const response = await fetch('http://localhost:8081/api/quizzes', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify(quizData)
      });
      
      if (!response.ok) {
        throw new Error('Failed to create quiz');
      }
      
      return await response.json();
    } catch (error) {
      console.error('Error creating quiz:', error);
      throw error;
    }
  },
  
  addQuestionToQuiz: async (quizId, questionData) => {
    try {
      const response = await fetch(`http://localhost:8081/api/quizzes/${quizId}/questions`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify(questionData)
      });
      
      if (!response.ok) {
        throw new Error('Failed to add question');
      }
      
      return await response.json();
    } catch (error) {
      console.error('Error adding question:', error);
      throw error;
    }
  }
};

const CreateQuiz = () => {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  
  // Quiz Details
  const [quizDetails, setQuizDetails] = useState({
    title: '',
    description: '',
    category: 'JAVA',
    difficulty: 'MEDIUM',
    timeLimitMinutes: 30,
    status: 'DRAFT',
    visibility: 'PUBLIC',
    allowMultipleAttempts: true,
    randomizeQuestions: false,
    randomizeOptions: false,
    negativeMarking: false,
    passingPercentage: 60,
    maxAttempts: 3
  });
  
  // Questions
  const [questions, setQuestions] = useState([
    {
      id: Date.now(),
      questionText: '',
      questionType: 'SINGLE_CORRECT',
      optionA: '',
      optionB: '',
      optionC: '',
      optionD: '',
      optionE: '',
      optionF: '',
      correctAnswer: '',
      correctAnswers: [],
      explanation: '',
      marks: 1,
      questionOrder: 1
    }
  ]);

  const categories = [
    'JAVA', 'DSA', 'APTITUDE', 'GENERAL_KNOWLEDGE', 'SCIENCE', 'MATHEMATICS', 
    'HISTORY', 'GEOGRAPHY', 'SPORTS', 'TECHNOLOGY', 'PROGRAMMING', 'DATABASES', 
    'WEB_DEVELOPMENT', 'MOBILE_DEVELOPMENT', 'CLOUD_COMPUTING', 'ARTIFICIAL_INTELLIGENCE', 
    'CYBERSECURITY', 'OTHER'
  ];

  const difficulties = ['EASY', 'MEDIUM', 'HARD'];
  const statuses = ['DRAFT', 'PUBLISHED', 'ARCHIVED'];
  const visibilities = ['PUBLIC', 'PRIVATE'];
  const questionTypes = ['SINGLE_CORRECT', 'MULTIPLE_CORRECT', 'TRUE_FALSE'];

  const handleQuizDetailsChange = (e) => {
    const { name, value, type, checked } = e.target;
    setQuizDetails(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : type === 'number' ? parseInt(value) : value
    }));
  };

  const handleQuestionChange = (index, field, value) => {
    const updatedQuestions = [...questions];
    updatedQuestions[index] = {
      ...updatedQuestions[index],
      [field]: value
    };
    setQuestions(updatedQuestions);
  };

  const addQuestion = () => {
    const newQuestion = {
      id: Date.now(),
      questionText: '',
      questionType: 'SINGLE_CORRECT',
      optionA: '',
      optionB: '',
      optionC: '',
      optionD: '',
      optionE: '',
      optionF: '',
      correctAnswer: '',
      correctAnswers: [],
      explanation: '',
      marks: 1,
      questionOrder: questions.length + 1
    };
    setQuestions([...questions, newQuestion]);
  };

  const removeQuestion = (index) => {
    if (questions.length > 1) {
      const updatedQuestions = questions.filter((_, i) => i !== index);
      // Reorder questions
      updatedQuestions.forEach((q, i) => {
        q.questionOrder = i + 1;
      });
      setQuestions(updatedQuestions);
    }
  };

  const handleCorrectAnswerChange = (index, value) => {
    const question = questions[index];
    if (question.questionType === 'MULTIPLE_CORRECT') {
      const correctAnswers = question.correctAnswers || [];
      if (correctAnswers.includes(value)) {
        // Remove from correct answers
        handleQuestionChange(index, 'correctAnswers', correctAnswers.filter(ans => ans !== value));
      } else {
        // Add to correct answers
        handleQuestionChange(index, 'correctAnswers', [...correctAnswers, value]);
      }
    } else {
      handleQuestionChange(index, 'correctAnswer', value);
    }
  };

  const validateQuiz = () => {
    if (!quizDetails.title.trim()) {
      setError('Quiz title is required');
      return false;
    }
    if (!quizDetails.description.trim()) {
      setError('Quiz description is required');
      return false;
    }
    
    for (let i = 0; i < questions.length; i++) {
      const q = questions[i];
      if (!q.questionText.trim()) {
        setError(`Question ${i + 1} text is required`);
        return false;
      }
      
      if (q.questionType === 'TRUE_FALSE') {
        if (!q.optionA.trim() || !q.optionB.trim()) {
          setError(`Question ${i + 1} requires both True and False options`);
          return false;
        }
      } else {
        if (!q.optionA.trim() || !q.optionB.trim() || !q.optionC.trim() || !q.optionD.trim()) {
          setError(`Question ${i + 1} requires at least 4 options`);
          return false;
        }
      }
      
      if (q.questionType === 'SINGLE_CORRECT' && !q.correctAnswer) {
        setError(`Question ${i + 1} requires a correct answer`);
        return false;
      }
      
      if (q.questionType === 'MULTIPLE_CORRECT' && (!q.correctAnswers || q.correctAnswers.length === 0)) {
        setError(`Question ${i + 1} requires at least one correct answer`);
        return false;
      }
    }
    
    return true;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    
    if (!validateQuiz()) {
      return;
    }
    
    setLoading(true);
    
    try {
      // Create quiz first
      const quizResponse = await quizService.createQuiz(quizDetails);
      const quizId = quizResponse.id;
      
      // Add questions to the quiz
      for (const question of questions) {
        await quizService.addQuestionToQuiz(quizId, question);
      }
      
      setSuccess('Quiz created successfully!');
      setTimeout(() => {
        navigate('/admin');
      }, 2000);
    } catch (err) {
      setError('Failed to create quiz. Please try again.');
      console.error('Error creating quiz:', err);
    } finally {
      setLoading(false);
    }
  };

  const renderQuestionOptions = (question, index) => {
    const { questionType } = question;
    
    if (questionType === 'TRUE_FALSE') {
      return (
        <div className="space-y-2">
          <div className="flex items-center space-x-2">
            <input
              type="radio"
              name={`correct-${index}`}
              value="A"
              checked={question.correctAnswer === 'A'}
              onChange={() => handleCorrectAnswerChange(index, 'A')}
              className="form-radio"
            />
            <input
              type="text"
              placeholder="True option"
              value={question.optionA}
              onChange={(e) => handleQuestionChange(index, 'optionA', e.target.value)}
              className="flex-1 px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
            />
          </div>
          <div className="flex items-center space-x-2">
            <input
              type="radio"
              name={`correct-${index}`}
              value="B"
              checked={question.correctAnswer === 'B'}
              onChange={() => handleCorrectAnswerChange(index, 'B')}
              className="form-radio"
            />
            <input
              type="text"
              placeholder="False option"
              value={question.optionB}
              onChange={(e) => handleQuestionChange(index, 'optionB', e.target.value)}
              className="flex-1 px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
            />
          </div>
        </div>
      );
    }
    
    const options = ['A', 'B', 'C', 'D', 'E', 'F'];
    const optionFields = ['optionA', 'optionB', 'optionC', 'optionD', 'optionE', 'optionF'];
    
    return (
      <div className="space-y-2">
        {options.slice(0, questionType === 'MULTIPLE_CORRECT' ? 6 : 4).map((option, optionIndex) => (
          <div key={option} className="flex items-center space-x-2">
            {questionType === 'MULTIPLE_CORRECT' ? (
              <input
                type="checkbox"
                checked={question.correctAnswers?.includes(option) || false}
                onChange={() => handleCorrectAnswerChange(index, option)}
                className="form-checkbox"
              />
            ) : (
              <input
                type="radio"
                name={`correct-${index}`}
                value={option}
                checked={question.correctAnswer === option}
                onChange={() => handleCorrectAnswerChange(index, option)}
                className="form-radio"
              />
            )}
            <input
              type="text"
              placeholder={`Option ${option}`}
              value={question[optionFields[optionIndex]] || ''}
              onChange={(e) => handleQuestionChange(index, optionFields[optionIndex], e.target.value)}
              className="flex-1 px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
            />
          </div>
        ))}
      </div>
    );
  };

  return (
    <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      {/* Header */}
      <div className="mb-8">
        <div className="flex items-center justify-between">
          <div className="flex items-center space-x-4">
            <button
              onClick={() => navigate('/admin/dashboard')}
              className="p-2 text-gray-600 hover:text-gray-900"
            >
              <FiArrowLeft className="h-5 w-5" />
            </button>
            <h1 className="text-3xl font-bold text-gray-900">Create Quiz</h1>
          </div>
        </div>
      </div>

      {/* Error/Success Messages */}
      {error && (
        <div className="mb-6 p-4 bg-red-100 border border-red-400 text-red-700 rounded-md">
          {error}
        </div>
      )}
      {success && (
        <div className="mb-6 p-4 bg-green-100 border border-green-400 text-green-700 rounded-md">
          {success}
        </div>
      )}

      <form onSubmit={handleSubmit} className="space-y-8">
        {/* Quiz Details */}
        <div className="bg-white rounded-lg shadow-md p-6">
          <h2 className="text-xl font-semibold text-gray-900 mb-6">Quiz Details</h2>
          
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Title *
              </label>
              <input
                type="text"
                name="title"
                value={quizDetails.title}
                onChange={handleQuizDetailsChange}
                className="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
                placeholder="Enter quiz title"
                required
              />
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Category *
              </label>
              <select
                name="category"
                value={quizDetails.category}
                onChange={handleQuizDetailsChange}
                className="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
              >
                {categories.map(cat => (
                  <option key={cat} value={cat}>{cat}</option>
                ))}
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Difficulty *
              </label>
              <select
                name="difficulty"
                value={quizDetails.difficulty}
                onChange={handleQuizDetailsChange}
                className="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
              >
                {difficulties.map(diff => (
                  <option key={diff} value={diff}>{diff}</option>
                ))}
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Time Limit (minutes) *
              </label>
              <input
                type="number"
                name="timeLimitMinutes"
                value={quizDetails.timeLimitMinutes}
                onChange={handleQuizDetailsChange}
                min="1"
                max="180"
                className="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
                required
              />
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Status
              </label>
              <select
                name="status"
                value={quizDetails.status}
                onChange={handleQuizDetailsChange}
                className="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
              >
                {statuses.map(status => (
                  <option key={status} value={status}>{status}</option>
                ))}
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Visibility
              </label>
              <select
                name="visibility"
                value={quizDetails.visibility}
                onChange={handleQuizDetailsChange}
                className="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
              >
                {visibilities.map(vis => (
                  <option key={vis} value={vis}>{vis}</option>
                ))}
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Passing Percentage
              </label>
              <input
                type="number"
                name="passingPercentage"
                value={quizDetails.passingPercentage}
                onChange={handleQuizDetailsChange}
                min="0"
                max="100"
                className="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
              />
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Max Attempts
              </label>
              <input
                type="number"
                name="maxAttempts"
                value={quizDetails.maxAttempts}
                onChange={handleQuizDetailsChange}
                min="1"
                max="10"
                className="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
              />
            </div>
          </div>
          
          <div className="mt-6">
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Description *
            </label>
            <textarea
              name="description"
              value={quizDetails.description}
              onChange={handleQuizDetailsChange}
              rows="3"
              className="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
              placeholder="Enter quiz description"
              required
            />
          </div>
          
          <div className="mt-6 space-y-3">
            <div className="flex items-center space-x-2">
              <input
                type="checkbox"
                name="allowMultipleAttempts"
                checked={quizDetails.allowMultipleAttempts}
                onChange={handleQuizDetailsChange}
                className="form-checkbox"
              />
              <label className="text-sm text-gray-700">Allow Multiple Attempts</label>
            </div>
            
            <div className="flex items-center space-x-2">
              <input
                type="checkbox"
                name="randomizeQuestions"
                checked={quizDetails.randomizeQuestions}
                onChange={handleQuizDetailsChange}
                className="form-checkbox"
              />
              <label className="text-sm text-gray-700">Randomize Questions</label>
            </div>
            
            <div className="flex items-center space-x-2">
              <input
                type="checkbox"
                name="randomizeOptions"
                checked={quizDetails.randomizeOptions}
                onChange={handleQuizDetailsChange}
                className="form-checkbox"
              />
              <label className="text-sm text-gray-700">Randomize Options</label>
            </div>
            
            <div className="flex items-center space-x-2">
              <input
                type="checkbox"
                name="negativeMarking"
                checked={quizDetails.negativeMarking}
                onChange={handleQuizDetailsChange}
                className="form-checkbox"
              />
              <label className="text-sm text-gray-700">Enable Negative Marking</label>
            </div>
          </div>
        </div>

        {/* Questions */}
        <div className="bg-white rounded-lg shadow-md p-6">
          <div className="flex items-center justify-between mb-6">
            <h2 className="text-xl font-semibold text-gray-900">Questions</h2>
            <button
              type="button"
              onClick={addQuestion}
              className="flex items-center space-x-2 px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700"
            >
              <FiPlus />
              <span>Add Question</span>
            </button>
          </div>
          
          <div className="space-y-6">
            {questions.map((question, index) => (
              <div key={question.id} className="border border-gray-200 rounded-lg p-6">
                <div className="flex items-center justify-between mb-4">
                  <h3 className="text-lg font-medium text-gray-900">Question {index + 1}</h3>
                  {questions.length > 1 && (
                    <button
                      type="button"
                      onClick={() => removeQuestion(index)}
                      className="text-red-600 hover:text-red-800"
                    >
                      <FiTrash2 />
                    </button>
                  )}
                </div>
                
                <div className="space-y-4">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      Question Type
                    </label>
                    <select
                      value={question.questionType}
                      onChange={(e) => handleQuestionChange(index, 'questionType', e.target.value)}
                      className="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
                    >
                      {questionTypes.map(type => (
                        <option key={type} value={type}>{type.replace('_', ' ')}</option>
                      ))}
                    </select>
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      Question Text *
                    </label>
                    <textarea
                      value={question.questionText}
                      onChange={(e) => handleQuestionChange(index, 'questionText', e.target.value)}
                      rows="3"
                      className="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
                      placeholder="Enter your question"
                      required
                    />
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      Options *
                    </label>
                    {renderQuestionOptions(question, index)}
                  </div>
                  
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">
                        Marks
                      </label>
                      <input
                        type="number"
                        value={question.marks}
                        onChange={(e) => handleQuestionChange(index, 'marks', parseInt(e.target.value))}
                        min="1"
                        max="10"
                        className="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
                      />
                    </div>
                    
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">
                        Explanation (Optional)
                      </label>
                      <input
                        type="text"
                        value={question.explanation}
                        onChange={(e) => handleQuestionChange(index, 'explanation', e.target.value)}
                        className="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
                        placeholder="Explain the correct answer"
                      />
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Submit Button */}
        <div className="flex justify-end space-x-4">
          <button
            type="button"
            onClick={() => navigate('/admin/dashboard')}
            className="px-6 py-2 border border-gray-300 text-gray-700 rounded-md hover:bg-gray-50"
          >
            Cancel
          </button>
          <button
            type="submit"
            disabled={loading}
            className="flex items-center space-x-2 px-6 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 disabled:opacity-50"
          >
            <FiSave />
            <span>{loading ? 'Creating...' : 'Create Quiz'}</span>
          </button>
        </div>
      </form>
    </div>
  );
};

export default CreateQuiz;
