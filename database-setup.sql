-- QuizMaster PostgreSQL Database Setup
-- Run this script in PostgreSQL to create the database and user

-- Create database
CREATE DATABASE quizmaster;

-- Create user
CREATE USER quizmaster WITH PASSWORD 'quizmaster123';

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE quizmaster TO quizmaster;

-- Connect to the database
\c quizmaster;

-- Grant schema privileges
GRANT ALL ON SCHEMA public TO quizmaster;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO quizmaster;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO quizmaster;

-- Set default privileges
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO quizmaster;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO quizmaster;
