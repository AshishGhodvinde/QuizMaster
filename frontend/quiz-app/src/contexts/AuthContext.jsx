import React, { createContext, useState, useContext, useEffect } from 'react';
import authService from '../services/authService';

const AuthContext = createContext();

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

export const AuthProvider = ({ children }) => {
  const [authState, setAuthState] = useState({
    token: localStorage.getItem('token'),
    isAuthenticated: null,
    loading: true,
    user: null
  });

  useEffect(() => {
    const loadUser = async () => {
      const token = localStorage.getItem('token');
      if (token) {
        try {
          const user = await authService.getCurrentUser();
          setAuthState({
            token: token,
            isAuthenticated: true,
            loading: false,
            user
          });
        } catch (error) {
          console.error('Failed to load user:', error);
          setAuthState({
            token: null,
            isAuthenticated: false,
            loading: false,
            user: null
          });
          localStorage.removeItem('token');
        }
      } else {
        setAuthState({
          token: null,
          isAuthenticated: false,
          loading: false,
          user: null
        });
      }
    };

    loadUser();
  }, []);

  const login = async (userData) => {
    try {
      const res = await authService.login(userData);
      localStorage.setItem('token', res.data.accessToken);
      
      setAuthState({
        ...authState,
        token: res.data.accessToken,
        isAuthenticated: true,
        loading: false,
        user: res.data.user
      });
      
      return res;
    } catch (error) {
      setAuthState({
        ...authState,
        token: null,
        isAuthenticated: false,
        loading: false,
        user: null
      });
      localStorage.removeItem('token');
      throw error;
    }
  };

  const register = async (userData) => {
    try {
      const res = await authService.register(userData);
      return res;
    } catch (error) {
      throw error;
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    setAuthState({
      token: null,
      isAuthenticated: false,
      loading: false,
      user: null
    });
  };

  const value = {
    ...authState,
    login,
    register,
    logout
  };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
};