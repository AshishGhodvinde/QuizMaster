# Test Quiz Application API Endpoints
Write-Host "Testing Quiz Application API..." -ForegroundColor Green

$baseUrl = "http://localhost:8081/api"

# Test Registration
Write-Host "`n1. Testing User Registration..." -ForegroundColor Yellow
try {
    $registerBody = @{
        username = "testuser"
        email = "test@example.com"
        password = "testpass123"
    } | ConvertTo-Json
    
    $registerResponse = Invoke-RestMethod -Uri "$baseUrl/auth/signup" -Method Post -Body $registerBody -ContentType "application/json"
    Write-Host "✅ Registration successful!" -ForegroundColor Green
    Write-Host "Response: $registerResponse" -ForegroundColor Cyan
} catch {
    Write-Host "❌ Registration failed: $($_.Exception.Message)" -ForegroundColor Red
}

# Test Login with default user
Write-Host "`n2. Testing Login with default user..." -ForegroundColor Yellow
try {
    $loginBody = @{
        username = "user"
        password = "user123"
    } | ConvertTo-Json
    
    $loginResponse = Invoke-RestMethod -Uri "$baseUrl/auth/signin" -Method Post -Body $loginBody -ContentType "application/json"
    Write-Host "✅ Login successful!" -ForegroundColor Green
    Write-Host "Token: $($loginResponse.accessToken.Substring(0, 20))..." -ForegroundColor Cyan
    Write-Host "User: $($loginResponse.user.username) (Role: $($loginResponse.user.role))" -ForegroundColor Cyan
    
    # Store token for further tests
    $token = $loginResponse.accessToken
} catch {
    Write-Host "❌ Login failed: $($_.Exception.Message)" -ForegroundColor Red
}

# Test Login with admin user
Write-Host "`n3. Testing Login with admin user..." -ForegroundColor Yellow
try {
    $adminLoginBody = @{
        username = "admin"
        password = "admin123"
    } | ConvertTo-Json
    
    $adminLoginResponse = Invoke-RestMethod -Uri "$baseUrl/auth/signin" -Method Post -Body $adminLoginBody -ContentType "application/json"
    Write-Host "✅ Admin login successful!" -ForegroundColor Green
    Write-Host "Token: $($adminLoginResponse.accessToken.Substring(0, 20))..." -ForegroundColor Cyan
    Write-Host "User: $($adminLoginResponse.user.username) (Role: $($adminLoginResponse.user.role))" -ForegroundColor Cyan
} catch {
    Write-Host "❌ Admin login failed: $($_.Exception.Message)" -ForegroundColor Red
}

# Test Get Quizzes (with token)
if ($token) {
    Write-Host "`n4. Testing Get All Quizzes..." -ForegroundColor Yellow
    try {
        $headers = @{
            "Authorization" = "Bearer $token"
        }
        
        $quizzesResponse = Invoke-RestMethod -Uri "$baseUrl/quizzes" -Method Get -Headers $headers
        Write-Host "✅ Quizzes retrieved successfully!" -ForegroundColor Green
        Write-Host "Found $($quizzesResponse.Count) quizzes:" -ForegroundColor Cyan
        
        foreach ($quiz in $quizzesResponse) {
            Write-Host "  - $($quiz.title) (ID: $($quiz.id))" -ForegroundColor White
        }
    } catch {
        Write-Host "❌ Failed to get quizzes: $($_.Exception.Message)" -ForegroundColor Red
    }
}

Write-Host "`nAPI Testing Complete!" -ForegroundColor Green
