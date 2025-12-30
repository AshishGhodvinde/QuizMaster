import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { FiBook, FiBarChart2, FiUsers, FiAward } from 'react-icons/fi';

const Home = () => {
  const { isAuthenticated, loading } = useAuth();

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center">
        <div className="text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600 mx-auto mb-4"></div>
          <p className="text-gray-600">Loading...</p>
        </div>
      </div>
    );
  }

  const features = [
    {
      icon: <FiBook className="h-8 w-8 text-blue-600" />,
      title: "Diverse Quizzes",
      description: "Take quizzes on various topics including technology, science, history, and more."
    },
    {
      icon: <FiBarChart2 className="h-8 w-8 text-green-600" />,
      title: "Performance Tracking",
      description: "Track your progress and see how you improve over time with detailed analytics."
    },
    {
      icon: <FiUsers className="h-8 w-8 text-purple-600" />,
      title: "Leaderboards",
      description: "Compete with others and see where you rank on our global leaderboards."
    },
    {
      icon: <FiAward className="h-8 w-8 text-yellow-600" />,
      title: "Achievements",
      description: "Earn badges and certificates as you complete quizzes and reach milestones."
    }
  ];

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      {/* Hero Section */}
      <div className="text-center py-16 md:py-24">
        <h1 className="text-4xl md:text-6xl font-extrabold text-gray-900 mb-6">
          Master Knowledge with <span className="text-blue-600">QuizMaster</span>
        </h1>
        <p className="text-xl text-gray-600 max-w-3xl mx-auto mb-10">
          Challenge yourself with interactive quizzes, track your progress, and compete with others in a fun learning environment.
        </p>
        <div className="flex flex-col sm:flex-row justify-center gap-4">
          {isAuthenticated ? (
            <Link to="/quizzes" className="btn-primary text-lg px-8 py-3">
              Browse Quizzes
            </Link>
          ) : (
            <>
              <Link to="/login" className="btn-primary text-lg px-8 py-3">
                Get Started
              </Link>
              <Link to="/register" className="btn-secondary text-lg px-8 py-3">
                Create Account
              </Link>
            </>
          )}
        </div>
      </div>

      {/* Features Section */}
      <div className="py-16">
        <div className="text-center mb-16">
          <h2 className="text-3xl font-bold text-gray-900 mb-4">Why Choose QuizMaster?</h2>
          <p className="text-xl text-gray-600 max-w-3xl mx-auto">
            Our platform offers everything you need to make learning engaging and effective.
          </p>
        </div>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
          {features.map((feature, index) => (
            <div key={index} className="card p-6 text-center">
              <div className="flex justify-center mb-4">
                {feature.icon}
              </div>
              <h3 className="text-xl font-semibold text-gray-900 mb-2">{feature.title}</h3>
              <p className="text-gray-600">{feature.description}</p>
            </div>
          ))}
        </div>
      </div>

      {/* Stats Section */}
      <div className="py-16 bg-blue-50 rounded-2xl">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-16">
            <h2 className="text-3xl font-bold text-gray-900 mb-4">Trusted by Thousands</h2>
            <p className="text-xl text-gray-600">
              Join our growing community of learners and educators
            </p>
          </div>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8 text-center">
            <div>
              <div className="text-5xl font-bold text-blue-600 mb-2">50K+</div>
              <div className="text-xl text-gray-700">Active Users</div>
            </div>
            <div>
              <div className="text-5xl font-bold text-blue-600 mb-2">1000+</div>
              <div className="text-xl text-gray-700">Quizzes Available</div>
            </div>
            <div>
              <div className="text-5xl font-bold text-blue-600 mb-2">98%</div>
              <div className="text-xl text-gray-700">Satisfaction Rate</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;
