import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { FiBook, FiBarChart2, FiClock, FiAward } from 'react-icons/fi';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';

// Temporary inline quizService until file system issue is resolved
const quizService = {
  getAllQuizzes: async () => {
    try {
      const token = localStorage.getItem('token');
      const headers = {
        'Content-Type': 'application/json'
      };
      
      if (token) {
        headers['Authorization'] = `Bearer ${token}`;
      }
      
      const response = await fetch('http://localhost:8081/api/quizzes', {
        method: 'GET',
        headers
      });
      
      if (!response.ok) {
        throw new Error('Failed to fetch quizzes');
      }
      
      return await response.json();
    } catch (error) {
      console.error('Error fetching quizzes:', error);
      return [];
    }
  },
  getMyAttempts: async () => {
    try {
      const token = localStorage.getItem('token');
      const headers = {
        'Content-Type': 'application/json'
      };
      
      if (token) {
        headers['Authorization'] = `Bearer ${token}`;
      }
      
      const response = await fetch('http://localhost:8081/api/quizzes/attempts', {
        method: 'GET',
        headers
      });
      
      if (!response.ok) {
        throw new Error('Failed to fetch attempts');
      }
      
      return await response.json();
    } catch (error) {
      console.error('Error fetching attempts:', error);
      return [];
    }
  }
};

const Dashboard = () => {
  const { user } = useAuth();
  const [quizzes, setQuizzes] = useState([]);
  const [attempts, setAttempts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [stats, setStats] = useState({
    totalQuizzes: 0,
    completedQuizzes: 0,
    averageScore: 0,
    totalTimeSpent: 0
  });

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [quizzesData, attemptsData] = await Promise.all([
          quizService.getAllQuizzes(),
          quizService.getMyAttempts()
        ]);
        
        setQuizzes(quizzesData);
        setAttempts(attemptsData);
        
        // Calculate stats
        const totalQuizzes = quizzesData.length;
        const completedQuizzes = attemptsData.length;
        const averageScore = attemptsData.length > 0 
          ? Math.round(attemptsData.reduce((sum, attempt) => sum + (attempt.obtainedMarks / attempt.totalMarks * 100), 0) / attemptsData.length)
          : 0;
          
        setStats({
          totalQuizzes,
          completedQuizzes,
          averageScore,
          totalTimeSpent: completedQuizzes * 15 // Assuming 15 minutes per quiz
        });
        
        setLoading(false);
      } catch (error) {
        console.error('Error fetching dashboard data:', error);
        setLoading(false);
      }
    };
    
    fetchData();
  }, []);

  // Prepare chart data
  const chartData = attempts.slice(0, 5).map(attempt => ({
    name: attempt.quiz?.title || `Quiz ${attempt.quiz?.id}`,
    score: Math.round(attempt.obtainedMarks / attempt.totalMarks * 100)
  }));

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-blue-500"></div>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900">
          Welcome back, {user?.username}!
        </h1>
        <p className="mt-2 text-gray-600">
          Here's what's happening with your quizzes today.
        </p>
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        <div className="card p-6">
          <div className="flex items-center">
            <div className="p-3 rounded-full bg-blue-100">
              <FiBook className="h-6 w-6 text-blue-600" />
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-600">Total Quizzes</p>
              <p className="text-2xl font-semibold text-gray-900">{stats.totalQuizzes}</p>
            </div>
          </div>
        </div>
        
        <div className="card p-6">
          <div className="flex items-center">
            <div className="p-3 rounded-full bg-green-100">
              <FiAward className="h-6 w-6 text-green-600" />
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-600">Completed</p>
              <p className="text-2xl font-semibold text-gray-900">{stats.completedQuizzes}</p>
            </div>
          </div>
        </div>
        
        <div className="card p-6">
          <div className="flex items-center">
            <div className="p-3 rounded-full bg-yellow-100">
              <FiBarChart2 className="h-6 w-6 text-yellow-600" />
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-600">Avg. Score</p>
              <p className="text-2xl font-semibold text-gray-900">{stats.averageScore}%</p>
            </div>
          </div>
        </div>
        
        <div className="card p-6">
          <div className="flex items-center">
            <div className="p-3 rounded-full bg-purple-100">
              <FiClock className="h-6 w-6 text-purple-600" />
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-600">Time Spent</p>
              <p className="text-2xl font-semibold text-gray-900">{stats.totalTimeSpent}m</p>
            </div>
          </div>
        </div>
      </div>

      {/* Chart and Recent Activity */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8 mb-8">
        {/* Performance Chart */}
        <div className="card p-6">
          <h2 className="text-xl font-semibold text-gray-900 mb-4">Recent Performance</h2>
          <div className="h-80">
            <ResponsiveContainer width="100%" height="100%">
              <BarChart data={chartData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip />
                <Bar dataKey="score" fill="#3b82f6" />
              </BarChart>
            </ResponsiveContainer>
          </div>
        </div>
        
        {/* Recent Attempts */}
        <div className="card p-6">
          <h2 className="text-xl font-semibold text-gray-900 mb-4">Recent Attempts</h2>
          <div className="space-y-4">
            {attempts.slice(0, 5).map((attempt) => (
              <div key={attempt.id} className="flex items-center justify-between p-4 bg-gray-50 rounded-lg">
                <div>
                  <h3 className="font-medium text-gray-900">{attempt.quiz?.title || `Quiz ${attempt.quiz?.id}`}</h3>
                  <p className="text-sm text-gray-500">
                    {new Date(attempt.submittedAt).toLocaleDateString()}
                  </p>
                </div>
                <div className="text-right">
                  <p className="font-medium text-gray-900">
                    {attempt.correctAnswers}/{attempt.totalQuestions}
                  </p>
                  <p className="text-sm text-gray-500">
                    {Math.round(attempt.obtainedMarks / attempt.totalMarks * 100)}%
                  </p>
                </div>
              </div>
            ))}
            {attempts.length === 0 && (
              <p className="text-gray-500 text-center py-8">
                You haven't completed any quizzes yet.
              </p>
            )}
          </div>
        </div>
      </div>

      {/* Available Quizzes */}
      <div className="card p-6 mb-8">
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-xl font-semibold text-gray-900">Available Quizzes</h2>
          <Link to="/quizzes" className="text-blue-600 hover:text-blue-800 font-medium">
            View all
          </Link>
        </div>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {quizzes.slice(0, 3).map((quiz) => (
            <div key={quiz.id} className="border border-gray-200 rounded-lg p-6 hover:shadow-md transition-shadow">
              <h3 className="text-lg font-medium text-gray-900 mb-2">{quiz.title}</h3>
              <p className="text-gray-600 text-sm mb-4">{quiz.description}</p>
              <Link to={`/quizzes/${quiz.id}`} className="btn-primary w-full text-center">
                Start Quiz
              </Link>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default Dashboard;