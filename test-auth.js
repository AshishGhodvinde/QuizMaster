// Test authentication
const fetch = require('node-fetch');

async function createUser(userData) {
    try {
        const response = await fetch('http://localhost:8081/api/auth/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(userData)
        });
        
        const result = await response.text();
        console.log('User created:', userData.username);
        console.log('Response:', result);
        return result;
    } catch (error) {
        console.error('Error creating user:', error.message);
        throw error;
    }
}

async function testAuth() {
    console.log('Creating admin user...');
    await createUser({
        username: "admin",
        email: "admin@example.com", 
        password: "admin123",
        role: "ADMIN"
    });
    
    console.log('Creating regular user...');
    await createUser({
        username: "user",
        email: "user@example.com",
        password: "user123", 
        role: "USER"
    });
    
    console.log('Testing login...');
    try {
        const loginResponse = await fetch('http://localhost:8081/api/auth/signin', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                username: "admin",
                password: "admin123"
            })
        });
        
        const loginResult = await loginResponse.text();
        console.log('Login response:', loginResult);
    } catch (error) {
        console.error('Login error:', error.message);
    }
}

testAuth();
