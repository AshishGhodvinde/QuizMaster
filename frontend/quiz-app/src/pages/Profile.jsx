import React, { useState, useEffect } from 'react';
import { useAuth } from '../contexts/AuthContext';
import quizService from '../services/quizService';
import { FiUser, FiMail, FiCalendar, FiAward, FiBarChart2 } from 'react-icons/fi';

const Profile = () => {
  const { user } = useAuth();
  const [attempts, setAttempts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [activeTab, setActiveTab] = useState('profile');

  useEffect(() => {
    const fetchAttempts = async () => {
      try {
        const data = await quizService.getMyAttempts();
        setAttempts(data);
        setLoading(false);
      } catch (error) {
        console.error('Error fetching attempts:', error);
        setLoading(false);
      }
    };
    
    fetchAttempts();
  }, []);

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    });
  };

  if (!user) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-blue-500"></div>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900">Profile</h1>
        <p className="text-gray-600">Manage your account settings and view your activity</p>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Profile Sidebar */}
        <div className="lg:col-span-1">
          <div className="card p-6">
            <div className="flex flex-col items-center">
              <div className="mx-auto flex items-center justify-center h-20 w-20 rounded-full bg-blue-100 mb-4">
                <FiUser className="h-10 w-10 text-blue-600" />
              </div>
              <h2 className="text-xl font-bold text-gray-900">{user.username}</h2>
              <p className="text-gray-600 mb-2">{user.email}</p>
              <span className="inline-flex items-center px-3 py-1 rounded-full text-sm font-medium bg-blue-100 text-blue-800">
                {user.role}
              </span>
            </div>
            
            <div className="mt-6 space-y-4">
              <button
                onClick={() => setActiveTab('profile')}
                className={`w-full text-left px-4 py-2 rounded-md ${
                  activeTab === 'profile' 
                    ? 'bg-blue-50 text-blue-700' 
                    : 'text-gray-700 hover:bg-gray-100'
                }`}
              >
                Profile Information
              </button>
              <button
                onClick={() => setActiveTab('activity')}
                className={`w-full text-left px-4 py-2 rounded-md ${
                  activeTab === 'activity' 
                    ? 'bg-blue-50 text-blue-700' 
                    : 'text-gray-700 hover:bg-gray-100'
                }`}
              >
                Quiz Activity
              </button>
              <button
                onClick={() => setActiveTab('settings')}
                className={`w-full text-left px-4 py-2 rounded-md ${
                  activeTab === 'settings' 
                    ? 'bg-blue-50 text-blue-700' 
                    : 'text-gray-700 hover:bg-gray-100'
                }`}
              >
                Settings
              </button>
            </div>
          </div>
        </div>

        {/* Main Content */}
        <div className="lg:col-span-2">
          {activeTab === 'profile' && (
            <div className="card p-6">
              <h2 className="text-xl font-semibold text-gray-900 mb-6">Profile Information</h2>
              
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                  <label className="form-label">Username</label>
                  <div className="form-input bg-gray-50">
                    {user.username}
                  </div>
                </div>
                
                <div>
                  <label className="form-label">Email Address</label>
                  <div className="form-input bg-gray-50">
                    {user.email}
                  </div>
                </div>
                
                <div>
                  <label className="form-label">Member Since</label>
                  <div className="form-input bg-gray-50">
                    <div className="flex items-center">
                      <FiCalendar className="mr-2 text-gray-500" />
                      {formatDate(user.createdAt || new Date())}
                    </div>
                  </div>
                </div>
                
                <div>
                  <label className="form-label">Account Type</label>
                  <div className="form-input bg-gray-50">
                    <div className="flex items-center">
                      <FiAward className="mr-2 text-gray-500" />
                      {user.role}
                    </div>
                  </div>
                </div>
              </div>
              
              <div className="mt-8">
                <h3 className="text-lg font-medium text-gray-900 mb-4">Statistics</h3>
                <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                  <div className="border border-gray-200 rounded-lg p-4 text-center">
                    <div className="text-2xl font-bold text-blue-600">
                      {attempts.length}
                    </div>
                    <div className="text-gray-600">Quizzes Taken</div>
                  </div>
                  <div className="border border-gray-200 rounded-lg p-4 text-center">
                    <div className="text-2xl font-bold text-green-600">
                      {attempts.length > 0 
                        ? Math.round(attempts.reduce((sum, attempt) => 
                            sum + (attempt.score / attempt.totalQuestions * 100), 0) / attempts.length)
                        : 0}%
                    </div>
                    <div className="text-gray-600">Average Score</div>
                  </div>
                  <div className="border border-gray-200 rounded-lg p-4 text-center">
                    <div className="text-2xl font-bold text-purple-600">
                      {attempts.filter(a => a.score === a.totalQuestions).length}
                    </div>
                    <div className="text-gray-600">Perfect Scores</div>
                  </div>
                </div>
              </div>
            </div>
          )}

          {activeTab === 'activity' && (
            <div className="card p-6">
              <h2 className="text-xl font-semibold text-gray-900 mb-6">Quiz Activity</h2>
              
              {loading ? (
                <div className="flex justify-center py-8">
                  <div className="animate-spin rounded-full h-8 w-8 border-t-2 border-b-2 border-blue-500"></div>
                </div>
              ) : attempts.length > 0 ? (
                <div className="space-y-4">
                  {attempts.map((attempt) => (
                    <div key={attempt.id} className="border border-gray-200 rounded-lg p-4">
                      <div className="flex justify-between items-start">
                        <div>
                          <h3 className="font-medium text-gray-900">Quiz #{attempt.quiz.id}</h3>
                          <p className="text-sm text-gray-500">
                            Taken on {formatDate(attempt.dateTaken)}
                          </p>
                        </div>
                        <div className="text-right">
                          <div className="text-lg font-bold text-gray-900">
                            {attempt.score}/{attempt.totalQuestions}
                          </div>
                          <div className="text-sm text-gray-500">
                            {Math.round(attempt.score / attempt.totalQuestions * 100)}%
                          </div>
                        </div>
                      </div>
                      <div className="mt-3 w-full bg-gray-200 rounded-full h-2">
                        <div 
                          className="bg-blue-600 h-2 rounded-full" 
                          style={{ width: `${Math.round(attempt.score / attempt.totalQuestions * 100)}%` }}
                        ></div>
                      </div>
                    </div>
                  ))}
                </div>
              ) : (
                <div className="text-center py-8">
                  <FiBarChart2 className="mx-auto h-12 w-12 text-gray-400" />
                  <h3 className="mt-2 text-lg font-medium text-gray-900">No quiz activity yet</h3>
                  <p className="mt-1 text-gray-500">
                    Take your first quiz to see your activity here.
                  </p>
                </div>
              )}
            </div>
          )}

          {activeTab === 'settings' && (
            <div className="card p-6">
              <h2 className="text-xl font-semibold text-gray-900 mb-6">Account Settings</h2>
              
              <div className="space-y-6">
                <div>
                  <h3 className="text-lg font-medium text-gray-900 mb-4">Change Password</h3>
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div>
                      <label className="form-label">Current Password</label>
                      <input
                        type="password"
                        className="form-input"
                        placeholder="Enter current password"
                      />
                    </div>
                    <div></div>
                    <div>
                      <label className="form-label">New Password</label>
                      <input
                        type="password"
                        className="form-input"
                        placeholder="Enter new password"
                      />
                    </div>
                    <div>
                      <label className="form-label">Confirm New Password</label>
                      <input
                        type="password"
                        className="form-input"
                        placeholder="Confirm new password"
                      />
                    </div>
                  </div>
                  <div className="mt-4">
                    <button className="btn-primary">
                      Update Password
                    </button>
                  </div>
                </div>
                
                <div className="border-t border-gray-200 pt-6">
                  <h3 className="text-lg font-medium text-gray-900 mb-4">Notification Preferences</h3>
                  <div className="space-y-3">
                    <div className="flex items-center justify-between">
                      <div>
                        <p className="font-medium text-gray-900">Quiz Results</p>
                        <p className="text-sm text-gray-500">Receive notifications about your quiz results</p>
                      </div>
                      <button className="relative inline-flex h-6 w-11 flex-shrink-0 cursor-pointer rounded-full border-2 border-transparent transition-colors duration-200 ease-in-out focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 bg-blue-600">
                        <span className="sr-only">Use setting</span>
                        <span aria-hidden="true" className="pointer-events-none inline-block h-5 w-5 transform rounded-full bg-white shadow ring-0 transition duration-200 ease-in-out translate-x-5"></span>
                      </button>
                    </div>
                    
                    <div className="flex items-center justify-between">
                      <div>
                        <p className="font-medium text-gray-900">New Quizzes</p>
                        <p className="text-sm text-gray-500">Get notified about new quizzes</p>
                      </div>
                      <button className="relative inline-flex h-6 w-11 flex-shrink-0 cursor-pointer rounded-full border-2 border-transparent transition-colors duration-200 ease-in-out focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 bg-gray-200">
                        <span className="sr-only">Use setting</span>
                        <span aria-hidden="true" className="pointer-events-none inline-block h-5 w-5 transform rounded-full bg-white shadow ring-0 transition duration-200 ease-in-out translate-x-0"></span>
                      </button>
                    </div>
                  </div>
                </div>
                
                <div className="border-t border-gray-200 pt-6">
                  <h3 className="text-lg font-medium text-gray-900 mb-4">Delete Account</h3>
                  <p className="text-gray-600 mb-4">
                    Permanently delete your account and all associated data. This action cannot be undone.
                  </p>
                  <button className="btn-danger">
                    Delete Account
                  </button>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Profile;