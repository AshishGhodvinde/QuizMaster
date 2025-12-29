# Quiz Application - Local Development Guide

## Quick Start

### Prerequisites
- Java 21+
- Node.js 18+
- Maven 3.8+

### Running the Application

#### Option 1: Using the PowerShell Script (Recommended)
```powershell
.\start-local.ps1
```

#### Option 2: Manual Start

**Backend:**
```bash
cd backend
mvn clean package -DskipTests
java -jar target/quiz-application-0.0.1-SNAPSHOT.jar
```

**Frontend:**
```bash
cd frontend/quiz-app
npm install
npm run dev
```

### Access Points

- **Frontend Application**: http://localhost:3000
- **Backend API**: http://localhost:8081
- **H2 Database Console**: http://localhost:8081/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: `password`

### Default Accounts

The application automatically creates default users on first startup:

- **Admin User**
  - Username: `admin`
  - Password: `admin123`
  - Role: ADMIN

- **Regular User**
  - Username: `user`
  - Password: `user123`
  - Role: USER

### Features

✅ **User Authentication**
- Registration and login
- JWT-based authentication
- Role-based access control

✅ **Quiz Management**
- Create, edit, delete quizzes (Admin only)
- Multiple choice questions
- Immediate feedback on answers

✅ **User Features**
- Take quizzes on various topics
- Track scores and progress
- View quiz history
- Performance analytics

✅ **Modern UI**
- Responsive design with Tailwind CSS
- Clean and intuitive interface
- Real-time updates

### Technology Stack

**Backend:**
- Spring Boot 3.2.0
- Spring Security
- Spring Data JPA
- H2 Database (in-memory for development)
- JWT Authentication
- Maven

**Frontend:**
- React 18
- React Router
- Tailwind CSS
- Axios
- Vite

### API Endpoints

#### Authentication
- `POST /api/auth/signup` - User registration
- `POST /api/auth/signin` - User login

#### Users
- `GET /api/users/me` - Get current user
- `GET /api/users` - Get all users (Admin only)

#### Quizzes
- `GET /api/quizzes` - Get all quizzes
- `POST /api/quizzes` - Create quiz (Admin only)
- `GET /api/quizzes/{id}` - Get quiz by ID
- `POST /api/quizzes/{id}/submit` - Submit quiz answers

### Development Notes

- The backend runs on port 8081
- The frontend runs on port 3000
- H2 database is used for local development (in-memory)
- All data is reset when the backend restarts
- CORS is configured to allow frontend on localhost:3000

### Troubleshooting

**Port already in use:**
- Check what's using the port: `netstat -ano | findstr :8081`
- Kill the process: `taskkill /F /PID <PID>`

**Frontend not connecting to backend:**
- Ensure both services are running
- Check the proxy configuration in `vite.config.js`
- Verify API base URL in `authService.js`

**Database issues:**
- Access H2 console at http://localhost:8081/h2-console
- Use the credentials mentioned above
- Tables are automatically created on startup

### Production Deployment

For production deployment:
1. Switch to PostgreSQL database
2. Update `application.properties`
3. Use environment variables for sensitive data
4. Configure proper CORS settings
5. Enable HTTPS

Refer to the main README.md for Docker deployment instructions.
