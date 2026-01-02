import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { FiSearch, FiBook, FiClock, FiBarChart2, FiTag, FiTrendingUp, FiUser } from 'react-icons/fi';

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
      return []; // Return empty array on error
    }
  }
};

const QuizList = () => {
  const [quizzes, setQuizzes] = useState([]);
  const [filteredQuizzes, setFilteredQuizzes] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [categories] = useState([
    'All', 'JAVA', 'DSA', 'APTITUDE', 'GENERAL_KNOWLEDGE', 'SCIENCE', 'MATHEMATICS', 'HISTORY', 'GEOGRAPHY', 'SPORTS', 'TECHNOLOGY', 'PROGRAMMING', 'DATABASES', 'WEB_DEVELOPMENT', 'MOBILE_DEVELOPMENT', 'CLOUD_COMPUTING', 'ARTIFICIAL_INTELLIGENCE', 'CYBERSECURITY', 'OTHER'
  ]);
  const [difficulties] = useState(['All', 'EASY', 'MEDIUM', 'HARD']);
  const [selectedCategory, setSelectedCategory] = useState('All');
  const [selectedDifficulty, setSelectedDifficulty] = useState('All');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchQuizzes = async () => {
      try {
        const data = await quizService.getAllQuizzes();
        setQuizzes(data);
        setFilteredQuizzes(data);
        setLoading(false);
      } catch (error) {
        console.error('Error fetching quizzes:', error);
        setLoading(false);
      }
    };
    
    fetchQuizzes();
  }, []);

  useEffect(() => {
    let result = quizzes;
    
    // Filter by search term
    if (searchTerm) {
      result = result.filter(quiz => 
        quiz.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
        quiz.description.toLowerCase().includes(searchTerm.toLowerCase())
      );
    }
    
    // Filter by category
    if (selectedCategory !== 'All') {
      result = result.filter(quiz => quiz.category === selectedCategory);
    }
    
    // Filter by difficulty
    if (selectedDifficulty !== 'All') {
      result = result.filter(quiz => quiz.difficulty === selectedDifficulty);
    }
    
    setFilteredQuizzes(result);
  }, [searchTerm, selectedCategory, selectedDifficulty, quizzes]);

  const getDifficultyColor = (difficulty) => {
    switch (difficulty) {
      case 'EASY': return 'text-green-600 bg-green-100';
      case 'MEDIUM': return 'text-yellow-600 bg-yellow-100';
      case 'HARD': return 'text-red-600 bg-red-100';
      default: return 'text-gray-600 bg-gray-100';
    }
  };

  const getCategoryColor = (category) => {
    const colors = {
      'JAVA': 'text-blue-600 bg-blue-100',
      'DSA': 'text-purple-600 bg-purple-100',
      'APTITUDE': 'text-indigo-600 bg-indigo-100',
      'SCIENCE': 'text-green-600 bg-green-100',
      'MATHEMATICS': 'text-orange-600 bg-orange-100',
      'HISTORY': 'text-yellow-600 bg-yellow-100',
      'GEOGRAPHY': 'text-teal-600 bg-teal-100',
      'SPORTS': 'text-red-600 bg-red-100',
      'TECHNOLOGY': 'text-cyan-600 bg-cyan-100',
      'PROGRAMMING': 'text-pink-600 bg-pink-100'
    };
    return colors[category] || 'text-gray-600 bg-gray-100';
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900 mb-2">Quizzes</h1>
        <p className="text-gray-600">Browse and take quizzes on various topics</p>
      </div>

      {/* Search and Filters */}
      <div className="card p-6 mb-8">
        <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-4">
          <div className="relative flex-1 max-w-md">
            <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
              <FiSearch className="h-5 w-5 text-gray-400" />
            </div>
            <input
              type="text"
              className="block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-md leading-5 bg-white placeholder-gray-500 focus:outline-none focus:placeholder-gray-400 focus:ring-1 focus:ring-blue-500 focus:border-blue-500"
              placeholder="Search quizzes..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </div>
          
          <div className="flex flex-wrap gap-2">
            {categories.map((category) => (
              <button
                key={category}
                onClick={() => setSelectedCategory(category)}
                className={`px-4 py-2 text-sm font-medium rounded-full ${
                  selectedCategory === category
                    ? 'bg-blue-600 text-white'
                    : 'bg-gray-200 text-gray-700 hover:bg-gray-300'
                }`}
              >
                {category}
              </button>
            ))}
          </div>
        </div>
      </div>

      {/* Quiz Grid */}
      {filteredQuizzes.length > 0 ? (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {filteredQuizzes.map((quiz) => (
            <div key={quiz.id} className="card overflow-hidden">
              <div className="p-6">
                <div className="flex items-center mb-4">
                  <div className="flex-shrink-0 h-12 w-12 bg-blue-100 rounded-lg flex items-center justify-center">
                    <FiBook className="h-6 w-6 text-blue-600" />
                  </div>
                  <div className="ml-4">
                    <h3 className="text-lg font-medium text-gray-900">{quiz.title}</h3>
                    <p className="text-sm text-gray-500">Created by {quiz.createdBy?.username || 'Unknown'}</p>
                  </div>
                </div>
                
                <p className="text-gray-600 mb-6">{quiz.description}</p>
                
                <div className="flex items-center justify-between text-sm text-gray-500 mb-6">
                  <div className="flex items-center">
                    <FiClock className="mr-1" />
                    <span>15 mins</span>
                  </div>
                  <div className="flex items-center">
                    <FiBarChart2 className="mr-1" />
                    <span>{quiz.questionCount || 0} questions</span>
                  </div>
                </div>
                
                <Link to={`/quizzes/${quiz.id}`} className="btn-primary w-full text-center">
                  Start Quiz
                </Link>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <div className="text-center py-12">
          <FiBook className="mx-auto h-12 w-12 text-gray-400" />
          <h3 className="mt-2 text-lg font-medium text-gray-900">No quizzes found</h3>
          <p className="mt-1 text-gray-500">
            {searchTerm ? 'Try adjusting your search terms' : 'Check back later for new quizzes'}
          </p>
        </div>
      )}
    </div>
  );
};

export default QuizList;