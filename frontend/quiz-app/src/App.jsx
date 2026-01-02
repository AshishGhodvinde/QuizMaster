import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import QuizList from './pages/QuizList';
import QuizDetail from './pages/QuizDetail';
import Profile from './pages/Profile';
import AdminHome from './pages/AdminHome';
import AdminDashboard from './pages/AdminDashboard';
import ManageUsers from './pages/ManageUsers';
import CreateQuiz from './pages/CreateQuiz';
import { AuthProvider } from './contexts/AuthContext';
import './App.css';

function App() {
  return (
    <AuthProvider>
      <Router>
        <div className="min-h-screen bg-gray-50">
          <Navbar />
          <main>
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
              <Route path="/dashboard" element={<Dashboard />} />
              <Route path="/quizzes" element={<QuizList />} />
              <Route path="/quizzes/:id" element={<QuizDetail />} />
              <Route path="/profile" element={<Profile />} />
              <Route path="/admin" element={<AdminHome />} />
              <Route path="/admin/dashboard" element={<AdminDashboard />} />
              <Route path="/admin/users" element={<ManageUsers />} />
              <Route path="/admin/create-quiz" element={<CreateQuiz />} />
            </Routes>
          </main>
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;