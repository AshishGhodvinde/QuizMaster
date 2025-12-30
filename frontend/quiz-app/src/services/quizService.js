import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8081/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
});

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

const quizService = {
  getAllQuizzes: async () => {
    try {
      const response = await api.get('/quizzes');
      return response.data;
    } catch (error) {
      console.error('Error fetching quizzes:', error);
      throw error;
    }
  },

  getQuizById: async (id) => {
    const response = await api.get(`/quizzes/${id}`);
    return response.data;
  },

  getQuestionsForQuiz: async (id) => {
    const response = await api.get(`/quizzes/${id}/questions`);
    return response.data;
  },

  createQuiz: async (quizData) => {
    try {
      const response = await api.post('/quizzes', quizData);
      return response.data;
    } catch (error) {
      console.error('Error creating quiz:', error);
      throw error;
    }
  },

  updateQuiz: async (id, quizData) => {
    const response = await api.put(`/quizzes/${id}`, quizData);
    return response.data;
  },

  deleteQuiz: async (id) => {
    const response = await api.delete(`/quizzes/${id}`);
    return response.data;
  },

  submitQuiz: async (id, answers) => {
    const response = await api.post(`/quizzes/${id}/submit`, { answers });
    return response.data;
  },

  getMyAttempts: async () => {
    const response = await api.get('/quizzes/attempts');
    return response.data;
  },

  getQuizAttempts: async (id) => {
    const response = await api.get(`/quizzes/${id}/attempts`);
    return response.data;
  },

  addQuestionToQuiz: async (quizId, questionData) => {
    const response = await api.post(`/quizzes/${quizId}/questions`, questionData);
    return response.data;
  },

  updateQuestion: async (questionId, questionData) => {
    const response = await api.put(`/quizzes/questions/${questionId}`, questionData);
    return response.data;
  },

  deleteQuestion: async (questionId) => {
    const response = await api.delete(`/quizzes/questions/${questionId}`);
    return response.data;
  }
};

export default quizService;
