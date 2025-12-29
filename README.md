# Online Quiz Application

A modern, scalable online quiz application built with Spring Boot, React, and PostgreSQL. This application allows users to take quizzes on various topics, track their progress, and compete on leaderboards.

![Quiz Application Screenshot](screenshots/dashboard.png)

## Features

### User Features
- User authentication (registration and login)
- Quiz browsing and taking
- Immediate feedback on answers
- Score tracking and history
- Performance analytics and charts
- Responsive and modern UI

### Admin Features
- Create, edit, and delete quizzes
- Manage quiz questions
- View user attempts and statistics
- User management

### Technical Features
- JWT-based authentication
- RESTful API
- Database persistence with PostgreSQL
- Docker containerization
- Responsive design with Tailwind CSS
- Role-based access control

## Technology Stack

### Backend
- **Spring Boot** - Java framework for building RESTful APIs
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database access
- **PostgreSQL** - Relational database
- **JWT** - Token-based authentication
- **Lombok** - Boilerplate code reduction
- **Maven** - Dependency management

### Frontend
- **React** - JavaScript library for UI
- **React Router** - Client-side routing
- **Tailwind CSS** - Utility-first CSS framework
- **Axios** - HTTP client
- **Recharts** - Data visualization
- **React Icons** - Icon library
- **Vite** - Build tool

### DevOps
- **Docker** - Containerization
- **Docker Compose** - Multi-container orchestration
- **Nginx** - Reverse proxy and web server

## Prerequisites

- Java 17+
- Node.js 18+
- Docker and Docker Compose (for containerized deployment)
- PostgreSQL 13+ (if running locally without Docker)
- Maven 3.8+ (for backend build)
- npm 8+ (for frontend build)

## Installation

### Option 1: Using Docker (Recommended)

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd QuizApplication
   ```

2. (Optional) Update environment variables in `docker-compose.yml`:
   ```yaml
   environment:
     POSTGRES_DB: quizdb
     POSTGRES_USER: quizuser
     POSTGRES_PASSWORD: quizpass
   ```

3. Build and run the application:
   ```bash
   docker-compose up --build
   ```

4. Access the application:
   - Frontend: http://localhost
   - Backend API: http://localhost:8080

5. Default accounts will be created automatically:
   - Admin: username `admin`, password `admin123`
   - User: username `user`, password `user123`

### Option 2: Manual Installation

#### Backend Setup

1. Set up PostgreSQL database:
   - Install PostgreSQL
   - Create database: `CREATE DATABASE quizdb;`
   - Create user: `CREATE USER quizuser WITH PASSWORD 'quizpass';`
   - Grant privileges: `GRANT ALL PRIVILEGES ON DATABASE quizdb TO quizuser;`

2. Update database configuration in `backend/src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/quizdb
   spring.datasource.username=quizuser
   spring.datasource.password=quizpass
   ```

3. Navigate to the backend directory:
   ```bash
   cd backend
   ```

4. Build the application:
   ```bash
   mvn clean package
   ```

5. Run the application:
   ```bash
   java -jar target/quiz-application-0.0.1-SNAPSHOT.jar
   ```

#### Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd frontend/quiz-app
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. (Optional) Update API base URL in `src/services/authService.js` and `src/services/quizService.js`:
   ```javascript
   const API_BASE_URL = 'http://localhost:8080/api';
   ```

4. Start the development server:
   ```bash
   npm run dev
   ```

5. Build for production:
   ```bash
   npm run build
   ```

## Default Accounts

After the first run, the application will create default accounts:

- **Admin User**
  - Username: `admin`
  - Password: `admin123`
  - Permissions: Full access to all features including quiz management

- **Regular User**
  - Username: `user`
  - Password: `user123`
  - Permissions: Can take quizzes, view results, and manage profile

## API Endpoints

### Authentication
- `POST /api/auth/signup` - User registration
- `POST /api/auth/signin` - User login

### Users
- `GET /api/users` - Get all users (Admin only)
- `GET /api/users/me` - Get current authenticated user
- `GET /api/users/{username}` - Get user by username

### Quizzes
- `GET /api/quizzes` - Get all quizzes
- `GET /api/quizzes/{id}` - Get quiz by ID
- `GET /api/quizzes/{id}/questions` - Get questions for a quiz
- `POST /api/quizzes` - Create a new quiz (Admin only)
- `PUT /api/quizzes/{id}` - Update a quiz (Admin only)
- `DELETE /api/quizzes/{id}` - Delete a quiz (Admin only)
- `POST /api/quizzes/{id}/submit` - Submit quiz answers
- `GET /api/quizzes/attempts` - Get quiz attempts for current user
- `GET /api/quizzes/{id}/attempts` - Get quiz attempts for a specific quiz (Admin only)
- `POST /api/quizzes/{id}/questions` - Add a question to a quiz (Admin only)
- `PUT /api/quizzes/questions/{questionId}` - Update a question (Admin only)
- `DELETE /api/quizzes/questions/{questionId}` - Delete a question (Admin only)

### Request/Response Examples

**User Registration:**
```json
POST /api/auth/signup
{
  "username": "newuser",
  "email": "user@example.com",
  "password": "password123"
}
```

**Quiz Submission:**
```json
POST /api/quizzes/1/submit
{
  "answers": ["A", "B", "C"]
}
```

## Project Structure

```
QuizApplication/
├── backend/                    # Spring Boot backend
│   ├── src/main/java/         # Java source code
│   │   ├── com.quiz.application
│   │   │   ├── config/        # Configuration classes
│   │   │   ├── controller/    # REST controllers
│   │   │   ├── exception/     # Exception handlers
│   │   │   ├── model/         # Entity classes
│   │   │   ├── payload/       # DTOs
│   │   │   ├── repository/    # Spring Data repositories
│   │   │   ├── security/      # Security configuration
│   │   │   └── service/       # Business logic services
│   ├── src/main/resources/    # Configuration files
│   │   └── db/migration/      # Database migrations
│   ├── Dockerfile             # Backend Docker configuration
│   └── pom.xml                # Maven dependencies
├── frontend/                  # React frontend
│   └── quiz-app/              # React application
│       ├── src/               # React source code
│       │   ├── components/    # Reusable UI components
│       │   ├── contexts/      # React context providers
│       │   ├── hooks/         # Custom hooks
│       │   ├── pages/         # Page components
│       │   ├── services/      # API service clients
│       │   └── utils/         # Utility functions
│       ├── Dockerfile         # Frontend Docker configuration
│       ├── nginx.conf         # Nginx configuration
│       └── vite.config.js     # Vite build configuration
├── .github/workflows/         # CI/CD workflows
├── docker-compose.yml         # Multi-container orchestration
└── README.md                 # This file
```

## Deployment

### Production Deployment

To deploy to production, you should:

1. Update the database credentials in `application.properties` or use environment variables
2. Configure HTTPS for security
3. Set up a reverse proxy (Nginx) for the backend
4. Use environment variables for sensitive configuration
5. Set up CI/CD pipeline for automated deployment

### Environment Variables

**Backend (.env file or system environment variables):**
```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://db-host:5432/quizdb
SPRING_DATASOURCE_USERNAME=quizuser
SPRING_DATASOURCE_PASSWORD=securepassword
JWT_SECRET=your-super-secret-jwt-key
JWT_EXPIRATION=86400000
```

**Frontend (.env file):**
```bash
VITE_API_BASE_URL=https://your-domain.com/api
```

### Docker Deployment

1. Build images:
   ```bash
   docker build -t quiz-backend ./backend
   docker build -t quiz-frontend ./frontend/quiz-app
   ```

2. Run containers:
   ```bash
   docker run -d --name quiz-db -e POSTGRES_DB=quizdb -e POSTGRES_USER=quizuser -e POSTGRES_PASSWORD=quizpass postgres:15
   docker run -d --name quiz-backend --link quiz-db:db -p 8080:8080 quiz-backend
   docker run -d --name quiz-frontend --link quiz-backend:backend -p 80:80 quiz-frontend
   ```

### Cloud Deployment Options

#### Heroku
1. Create apps for backend and frontend
2. Set environment variables in Heroku dashboard
3. Deploy using Heroku CLI or GitHub integration

#### AWS ECS
1. Create ECS cluster
2. Define task definitions for backend and frontend
3. Set up load balancers and security groups
4. Deploy services with Fargate

#### Google Cloud Run
1. Build and push Docker images to Container Registry
2. Deploy services to Cloud Run
3. Configure Cloud SQL for PostgreSQL
4. Set up Cloud Load Balancing

#### Azure Container Instances
1. Create container groups
2. Configure networking and DNS
3. Set up Azure Database for PostgreSQL
4. Deploy containers with environment variables

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a pull request

### Development Guidelines

- Follow the existing code style
- Write unit tests for new functionality
- Update documentation when making changes
- Ensure all tests pass before submitting a pull request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

If you encounter any issues or have questions, please [open an issue](../../issues) on GitHub.