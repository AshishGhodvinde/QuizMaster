# 🎯 QuizMaster - Interactive Quiz Platform

<div align="center">
  <img src="https://img.shields.io/badge/React-18.2.0-61DAFB?style=for-the-badge&logo=react&logoColor=white" alt="React">
  <img src="https://img.shields.io/badge/Spring_Boot-3.1.5-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" alt="Spring Boot">
  <img src="https://img.shields.io/badge/PostgreSQL-15.0-4169E1?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL">
  <img src="https://img.shields.io/badge/Maven-3.9.0-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white" alt="Maven">
</div>

<div align="center">
  <h3>A modern, full-stack quiz application built with React and Spring Boot</h3>
  <p>Features real-time quiz taking, admin dashboard, user management, and comprehensive analytics</p>
</div>

---

## 📋 Table of Contents

- [🚀 Features](#-features)
- [🛠️ Tech Stack](#️-tech-stack)
- [📸 Screenshots](#-screenshots)
- [🏗️ Architecture](#️-architecture)
- [⚙️ Installation](#️-installation)
- [🎮 Usage](#-usage)
- [👥 User Roles](#-user-roles)
- [🔧 Configuration](#-configuration)
- [📊 Database Schema](#-database-schema)
- [🚀 Deployment](#-deployment)
- [🤝 Contributing](#-contributing)
- [📄 License](#-license)

---

## 🚀 Features

### 🎯 Core Functionality
- **Interactive Quiz Taking** with real-time timer and progress tracking
- **Multiple Question Types**: Single correct, multiple correct, true/false
- **Instant Results & Feedback** with detailed score breakdown
- **Quiz History & Analytics** for performance tracking

### 👤 User Experience
- **Role-Based Access Control** (Admin/User)
- **JWT Authentication** for secure login
- **Responsive Design** works on all devices
- **Modern UI/UX** with Tailwind CSS

### 🛠️ Admin Features
- **Quiz Management** (Create, Edit, Delete)
- **User Management** with role assignment
- **Real-time Statistics** dashboard
- **Question Bank Management**
- **Quiz Configuration** (time limits, attempts, scoring)

### 🔒 Security & Performance
- **PostgreSQL Database** for reliable data storage
- **Spring Security** with JWT tokens
- **CORS Configuration** for secure API access
- **Input Validation** and error handling

---

## 🛠️ Tech Stack

### 🎨 Frontend
```
React 18.2.0          ⚛️  Modern UI framework
Vite                  🚀  Fast build tool
Tailwind CSS          🎨  Utility-first CSS framework
React Router          🛣️  Client-side routing
Axios                 📡  HTTP client
Lucide React         🎭  Beautiful icons
```

### 🚀 Backend
```
Spring Boot 3.1.5     🍃  Java framework
Spring Security       🔒  Authentication & authorization
Spring Data JPA       📊  Database ORM
PostgreSQL 15.0       🐘  Relational database
JWT                   🎫  Token-based auth
Maven                 📦  Build tool
Lombok               ⚡  Java code generation
```

---

## 📸 Screenshots

> 📝 **Note**: Add your screenshots in the `docs/screenshots/` directory and update the paths below

### 🏠 Home Page
<div align="center">
  <img src="docs/screenshots/home-page.png" alt="Home Page" width="800">
  <p><em>Welcome screen with quiz overview and navigation</em></p>
</div>

### 🔐 User Authentication
<div align="center">
  <img src="docs/screenshots/login-page.png" alt="Login Page" width="400">
  <img src="docs/screenshots/register-page.png" alt="Register Page" width="400">
  <p><em>Secure login and registration interface</em></p>
</div>

### 📊 User Dashboard
<div align="center">
  <img src="docs/screenshots/user-dashboard.png" alt="User Dashboard" width="800">
  <p><em>Personal dashboard with quiz history and statistics</em></p>
</div>

### 🎮 Quiz Taking Experience
<div align="center">
  <img src="docs/screenshots/quiz-taking.png" alt="Quiz Taking" width="800">
  <p><em>Interactive quiz interface with timer and progress bar</em></p>
</div>

### 📈 Quiz Results
<div align="center">
  <img src="docs/screenshots/quiz-results.png" alt="Quiz Results" width="800">
  <p><em>Detailed results with score breakdown and feedback</em></p>
</div>

### 🛠️ Admin Dashboard
<div align="center">
  <img src="docs/screenshots/admin-home.png" alt="Admin Home" width="800">
  <p><em>Admin control panel with quick actions</em></p>
</div>

### 👥 User Management
<div align="center">
  <img src="docs/screenshots/manage-users.png" alt="Manage Users" width="800">
  <p><em>Admin user management interface</em></p>
</div>

### 📝 Quiz Creation
<div align="center">
  <img src="docs/screenshots/create-quiz.png" alt="Create Quiz" width="800">
  <p><em>Intuitive quiz creation interface</em></p>
</div>

---

## 🏗️ Architecture

### 📐 System Design
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   React App     │    │  Spring Boot    │    │   PostgreSQL    │
│   (Frontend)    │◄──►│   (Backend)     │◄──►│   (Database)    │
│   Port: 3000    │    │   Port: 8081    │    │   Port: 5432    │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

### 🔄 Data Flow
1. **Frontend** sends HTTP requests to **Backend API**
2. **Backend** processes requests with **Spring Security**
3. **Database** operations handled by **Spring Data JPA**
4. **JWT tokens** manage authentication state
5. **Real-time updates** via RESTful API calls

---

## ⚙️ Installation

### 📋 Prerequisites
- **Node.js** (v18 or higher)
- **Java** (v17 or higher)
- **Maven** (v3.8 or higher)
- **PostgreSQL** (v15 or higher)

### 🗄️ Database Setup

1. **Create PostgreSQL Database**
   ```sql
   -- Run in PostgreSQL
   CREATE DATABASE quizmaster;
   CREATE USER quizmaster WITH PASSWORD 'quizmaster123';
   GRANT ALL PRIVILEGES ON DATABASE quizmaster TO quizmaster;
   ```

2. **Or use provided setup script**
   ```bash
   psql -U postgres -f database-setup.sql
   ```

### 🚀 Backend Setup

1. **Navigate to backend directory**
   ```bash
   cd backend
   ```

2. **Configure application properties**
   ```properties
   # backend/src/main/resources/application.properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/quizmaster
   spring.datasource.username=postgres
   spring.datasource.password=your_password
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   🌐 **Backend will be available at**: `http://localhost:8081`

### 🎨 Frontend Setup

1. **Navigate to frontend directory**
   ```bash
   cd frontend/quiz-app
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Start development server**
   ```bash
   npm run dev
   ```

   🌐 **Frontend will be available at**: `http://localhost:3000`

---

## 🎮 Usage

### 👤 Default Credentials

| Role    | Username | Password |
|---------|----------|----------|
| Admin   | admin    | admin123 |
| User    | user     | user123  |

### 🎯 Quick Start Guide

1. **Open** `http://localhost:3000` in your browser
2. **Login** with admin credentials (`admin/admin123`)
3. **Explore** the admin dashboard features
4. **Create** your first quiz with multiple questions
5. **Switch** to user account to test quiz taking
6. **View** results and analytics

### 📱 User Journey

1. **Registration/Login** → Secure authentication
2. **Browse Quizzes** → View available quizzes
3. **Take Quiz** → Interactive quiz experience
4. **View Results** → Instant feedback and scores
5. **Track Progress** → Personal dashboard

---

## 👥 User Roles

### 🔑 Admin Capabilities
- ✅ Create, edit, and delete quizzes
- ✅ Manage user accounts and roles
- ✅ View platform statistics and analytics
- ✅ Configure quiz settings and parameters
- ✅ Monitor quiz attempts and performance

### 🎮 User Capabilities
- ✅ Take available quizzes
- ✅ View personal quiz history
- ✅ Track performance statistics
- ✅ Manage profile information
- ✅ Attempt quizzes multiple times (if allowed)

---

## 🔧 Configuration

### 🛡️ Security Settings
```properties
# JWT Configuration
jwt.secret=your-secret-key-here
jwt.expiration=86400000

# CORS Configuration
spring.web.cors.allowed-origins=http://localhost:3000
```

### 📊 Database Configuration
```properties
# PostgreSQL Settings
spring.datasource.url=jdbc:postgresql://localhost:5432/quizmaster
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### ⏱️ Quiz Settings
```properties
# Default Quiz Configuration
quiz.default.time-limit=15
quiz.default.max-attempts=3
quiz.default.passing-percentage=60
```

---

## 📊 Database Schema

### 🗂️ Core Tables
```sql
users          ── User accounts and authentication
quizzes        ── Quiz definitions and settings
questions      ── Individual quiz questions
quiz_attempts  ── User quiz attempts and results
```

### 🔗 Relationships
- `users` ←→ `quizzes` (One-to-Many: Created By)
- `quizzes` ←→ `questions` (One-to-Many)
- `users` ←→ `quiz_attempts` (One-to-Many)
- `quizzes` ←→ `quiz_attempts` (One-to-Many)

---

## 🚀 Deployment

### 🐳 Docker Deployment (Optional)
```bash
# Build and run with Docker Compose
docker-compose up -d
```

### 🌐 Production Considerations
- **Environment Variables** for sensitive data
- **HTTPS Configuration** for secure connections
- **Database Backups** for data safety
- **Load Balancing** for scalability
- **Monitoring & Logging** for maintenance

---

## 🤝 Contributing

1. **Fork** the repository
2. **Create** a feature branch
   ```bash
   git checkout -b feature/amazing-feature
   ```
3. **Commit** your changes
   ```bash
   git commit -m 'Add amazing feature'
   ```
4. **Push** to the branch
   ```bash
   git push origin feature/amazing-feature
   ```
5. **Open** a Pull Request

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- **React Team** for the amazing frontend framework
- **Spring Boot Team** for the robust backend framework
- **PostgreSQL Team** for the reliable database system
- **Tailwind CSS** for the utility-first CSS framework

---

## 📞 Contact

<div align="center">
  <p>📧 For internship project review and inquiries</p>
  <p>🚀 Built with passion for interactive learning</p>
</div>

---

<div align="center">
  <img src="https://img.shields.io/github/stars/AshishGhodvinde/QuizMaster?style=social" alt="GitHub Stars">
  <img src="https://img.shields.io/github/forks/AshishGhodvinde/QuizMaster?style=social" alt="GitHub Forks">
  <img src="https://img.shields.io/github/issues/AshishGhodvinde/QuizMaster" alt="GitHub Issues">
  <img src="https://img.shields.io/github/license/AshishGhodvinde/QuizMaster" alt="License">
</div>
