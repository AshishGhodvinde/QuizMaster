# QuizMaster - Online Quiz Application

A fully functional online quiz application built with Spring Boot (backend) and React (frontend), using PostgreSQL for data persistence.

## 🚀 Features

### User Features
- **Authentication**: Secure login/registration with JWT tokens
- **Quiz Browsing**: Browse available quizzes by category and difficulty
- **Quiz Taking**: Interactive quiz interface with timer and navigation
- **Results & Scoring**: Detailed results with score breakdown
- **Quiz History**: Track all quiz attempts and performance statistics
- **Dashboard**: Personal dashboard with performance analytics

### Admin Features
- **Quiz Management**: Create, edit, and delete quizzes
- **Question Management**: Add multiple question types (Single Correct, Multiple Correct, True/False)
- **User Management**: Monitor user activity and quiz attempts
- **Admin Dashboard**: Comprehensive admin interface with statistics

## 🛠 Technology Stack

### Backend
- **Spring Boot 3.2.0** - Java framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database operations
- **PostgreSQL** - Primary database
- **JWT** - Token-based authentication
- **Flyway** - Database migrations
- **Maven** - Build tool

### Frontend
- **React 18** - UI framework
- **Vite** - Build tool and dev server
- **TailwindCSS** - CSS framework
- **React Router** - Client-side routing
- **Axios** - HTTP client

## 📋 Prerequisites

- Java 21 or higher
- Node.js 18 or higher
- PostgreSQL 14 or higher
- Maven 3.6 or higher

## 🗄 Database Setup

1. **Install PostgreSQL** on your system
2. **Create the database** by running the setup script:

```bash
psql -U postgres -f database-setup.sql
```

Or manually execute:
```sql
CREATE DATABASE quizmaster;
CREATE USER quizmaster WITH PASSWORD 'quizmaster123';
GRANT ALL PRIVILEGES ON DATABASE quizmaster TO quizmaster;
```

## 🚀 Installation & Setup

### Backend Setup

1. **Navigate to backend directory**:
```bash
cd backend
```

2. **Install dependencies**:
```bash
mvn clean install
```

3. **Run the application**:
```bash
mvn spring-boot:run
```

The backend will start on `http://localhost:8081`

### Frontend Setup

1. **Navigate to frontend directory**:
```bash
cd frontend/quiz-app
```

2. **Install dependencies**:
```bash
npm install
```

3. **Start development server**:
```bash
npm run dev
```

The frontend will start on `http://localhost:3000`

## 👤 Default Users

The application automatically creates default users on startup:

### Admin User
- **Username**: `admin`
- **Password**: `admin123`

### Regular User
- **Username**: `user`
- **Password**: `user123`

## 🎮 Usage

1. **Open the application** in your browser: `http://localhost:3000`
2. **Register** a new account or use default credentials
3. **Browse quizzes** and take them
4. **View your results** and track your progress
5. **Admin users** can create and manage quizzes

## 📊 Application Structure

```
QuizApplication/
├── backend/                    # Spring Boot backend
│   ├── src/main/java/
│   │   └── com/quiz/application/
│   │       ├── controller/     # REST controllers
│   │       ├── service/        # Business logic
│   │       ├── repository/     # Data access layer
│   │       ├── model/          # Entity classes
│   │       ├── payload/        # DTOs
│   │       ├── security/       # Security configuration
│   │       └── config/         # Application configuration
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
├── frontend/
│   └── quiz-app/               # React frontend
│       ├── src/
│       │   ├── components/     # Reusable components
│       │   ├── pages/          # Page components
│       │   ├── contexts/       # React contexts
│       │   ├── services/       # API services
│       │   └── App.jsx
│       ├── package.json
│       └── vite.config.js
└── database-setup.sql          # Database setup script
```

## 🔧 Configuration

### Database Configuration
Database settings are configured in `backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/quizmaster
spring.datasource.username=quizmaster
spring.datasource.password=quizmaster123
```

### JWT Configuration
JWT settings can be configured in the same file:
```properties
jwt.secret=your-secret-key
jwt.expiration=86400000  # 24 hours
```

## 🧪 Testing

### Backend Tests
```bash
cd backend
mvn test
```

### Frontend Tests
```bash
cd frontend/quiz-app
npm test
```

## 🚀 Deployment

### Backend Deployment
1. Build the JAR file:
```bash
mvn clean package
```

2. Run the JAR file:
```bash
java -jar target/quiz-application-0.0.1-SNAPSHOT.jar
```

### Frontend Deployment
1. Build for production:
```bash
npm run build
```

2. Deploy the `dist` folder to your web server

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Ensure PostgreSQL is running
   - Check database credentials in application.properties
   - Verify database was created using setup script

2. **Port Conflicts**
   - Backend uses port 8081
   - Frontend uses port 3000
   - Change ports if conflicts occur

3. **Build Errors**
   - Ensure Java 21+ is installed
   - Verify Maven is properly configured
   - Check Node.js version (18+)

## 📞 Support

For support and questions, please open an issue on the GitHub repository.
