import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8081/api';

// Create axios instance
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
});

// Add a request interceptor to include token in headers
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Quiz API calls
const quizService = {
  // Get all quizzes
  getAllQuizzes: async () => {
    const response = await api.get('/quizzes');
    return response.data;
  },

  // Get quiz by ID
  getQuizById: async (id) => {
    const response = await api.get(`/quizzes/${id}`);
    return response.data;
  },

  // Get questions for a quiz
  getQuestionsForQuiz: async (id) => {
    const response = await api.get(`/quizzes/${id}/questions`);
    return response.data;
  },

  // Create a new quiz (admin only)
  createQuiz: async (quizData) => {
    const response = await api.post('/quizzes', quizData);
    return response.data;
  },

  // Update a quiz (admin only)
  updateQuiz: async (id, quizData) => {
    const response = await api.put(`/quizzes/${id}`, quizData);
    return response.data;
  },

  // Delete a quiz (admin only)
  deleteQuiz: async (id) => {
    const response = await api.delete(`/quizzes/${id}`);
    return response.data;
  },

  // Submit quiz answers and calculate score
  submitQuiz: async (id, answers) => {
    const response = await api.post(`/quizzes/${id}/submit`, { answers });
    return response.data;
  },

  // Get quiz attempts for current user
  getMyAttempts: async () => {
    const response = await api.get('/quizzes/attempts');
    return response.data;
  },

  // Get quiz attempts for a specific quiz (admin only)
  getQuizAttempts: async (id) => {
    const response = await api.get(`/quizzes/${id}/attempts`);
    return response.data;
  },

  // Add a question to a quiz (admin only)
  addQuestionToQuiz: async (quizId, questionData) => {
    const response = await api.post(`/quizzes/${quizId}/questions`, questionData);
    return response.data;
  },

  // Update a question (admin only)
  updateQuestion: async (questionId, questionData) => {
    const response = await api.put(`/quizzes/questions/${questionId}`, questionData);
    return response.data;
  },

  // Delete a question (admin only)
  deleteQuestion: async (questionId) => {
    const response = await api.delete(`/quizzes/questions/${questionId}`);
    return response.data;
  }
};

export default quizService;