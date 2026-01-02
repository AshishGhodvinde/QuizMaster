import React from 'react';
import { Link } from 'react-router-dom';
import { FiPlus, FiUsers, FiBook, FiBarChart2, FiEdit, FiTrash2 } from 'react-icons/fi';

const AdminHome = () => {
  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900">Admin Dashboard</h1>
        <p className="text-gray-600">Welcome to QuizMaster Admin Panel</p>
      </div>

      {/* Quick Actions */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        <Link 
          to="/admin/create-quiz"
          className="card p-6 hover:shadow-lg transition-shadow duration-200"
        >
          <div className="flex items-center">
            <div className="p-3 rounded-full bg-blue-100">
              <FiPlus className="h-6 w-6 text-blue-600" />
            </div>
            <div className="ml-4">
              <h3 className="text-lg font-semibold text-gray-900">Create Quiz</h3>
              <p className="text-sm text-gray-500">Create a new quiz</p>
            </div>
          </div>
        </Link>

        <Link 
          to="/admin"
          className="card p-6 hover:shadow-lg transition-shadow duration-200"
        >
          <div className="flex items-center">
            <div className="p-3 rounded-full bg-green-100">
              <FiBook className="h-6 w-6 text-green-600" />
            </div>
            <div className="ml-4">
              <h3 className="text-lg font-semibold text-gray-900">Manage Quizzes</h3>
              <p className="text-sm text-gray-500">View and edit quizzes</p>
            </div>
          </div>
        </Link>

        <Link 
          to="/admin"
          className="card p-6 hover:shadow-lg transition-shadow duration-200"
        >
          <div className="flex items-center">
            <div className="p-3 rounded-full bg-purple-100">
              <FiBarChart2 className="h-6 w-6 text-purple-600" />
            </div>
            <div className="ml-4">
              <h3 className="text-lg font-semibold text-gray-900">View Statistics</h3>
              <p className="text-sm text-gray-500">View platform statistics</p>
            </div>
          </div>
        </Link>

        <Link 
          to="/admin"
          className="card p-6 hover:shadow-lg transition-shadow duration-200"
        >
          <div className="flex items-center">
            <div className="p-3 rounded-full bg-orange-100">
              <FiUsers className="h-6 w-6 text-orange-600" />
            </div>
            <div className="ml-4">
              <h3 className="text-lg font-semibold text-gray-900">Manage Users</h3>
              <p className="text-sm text-gray-500">View user accounts</p>
            </div>
          </div>
        </Link>
      </div>

      {/* Recent Activity */}
      <div className="card p-6">
        <h2 className="text-xl font-semibold text-gray-900 mb-4">Quick Start</h2>
        <div className="flex flex-col sm:flex-row gap-4">
          <Link 
            to="/admin/create-quiz"
            className="btn-primary inline-flex items-center justify-center"
          >
            <FiPlus className="mr-2" />
            Create New Quiz
          </Link>
        </div>
      </div>
    </div>
  );
};

export default AdminHome;
