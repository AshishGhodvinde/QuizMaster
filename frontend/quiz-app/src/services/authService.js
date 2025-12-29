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

// Auth API calls
const authService = {
  login: async (userData) => {
    const response = await api.post('/auth/signin', userData);
    return response;
  },

  register: async (userData) => {
    const response = await api.post('/auth/signup', userData);
    return response;
  },

  getCurrentUser: async () => {
    const response = await api.get('/users/me');
    return response.data;
  }
};

export default authService;