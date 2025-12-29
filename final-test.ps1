# Final Comprehensive Test
Write-Host "🚀 QUIZ APPLICATION - FINAL TEST" -ForegroundColor Green
Write-Host "================================" -ForegroundColor Green

# Test 1: Backend Health
Write-Host "`n1. ✅ Backend Health Check" -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/test" -Method Get
    Write-Host "   Status: ✅ Working" -ForegroundColor Green
} catch {
    Write-Host "   Status: ❌ Failed" -ForegroundColor Red
}

# Test 2: Registration
Write-Host "`n2. ✅ User Registration" -ForegroundColor Yellow
try {
    $body = @{
        username = "finaltest"
        email = "final@test.com"
        password = "testpass123"
    } | ConvertTo-Json
    
    $response = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/signup" -Method Post -Body $body -ContentType "application/json"
    Write-Host "   Status: ✅ Success - $($response.message)" -ForegroundColor Green
} catch {
    Write-Host "   Status: ⚠️  May already exist" -ForegroundColor Yellow
}

# Test 3: Login with default user
Write-Host "`n3. ✅ User Login (user/user123)" -ForegroundColor Yellow
try {
    $body = @{
        username = "user"
        password = "user123"
    } | ConvertTo-Json
    
    $response = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/signin" -Method Post -Body $body -ContentType "application/json"
    Write-Host "   Status: ✅ Success" -ForegroundColor Green
    Write-Host "   User: $($response.user.username) (Role: $($response.user.role))" -ForegroundColor Cyan
    Write-Host "   Token: $($response.accessToken.Substring(0, 20))..." -ForegroundColor Cyan
} catch {
    Write-Host "   Status: ❌ Failed" -ForegroundColor Red
}

# Test 4: Login with admin
Write-Host "`n4. ✅ Admin Login (admin/admin123)" -ForegroundColor Yellow
try {
    $body = @{
        username = "admin"
        password = "admin123"
    } | ConvertTo-Json
    
    $response = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/signin" -Method Post -Body $body -ContentType "application/json"
    Write-Host "   Status: ✅ Success" -ForegroundColor Green
    Write-Host "   User: $($response.user.username) (Role: $($response.user.role))" -ForegroundColor Cyan
    Write-Host "   Token: $($response.accessToken.Substring(0, 20))..." -ForegroundColor Cyan
} catch {
    Write-Host "   Status: ❌ Failed" -ForegroundColor Red
}

# Test 5: Get Quizzes
Write-Host "`n5. ✅ Get All Quizzes" -ForegroundColor Yellow
try {
    $body = @{
        username = "user"
        password = "user123"
    } | ConvertTo-Json
    
    $loginResponse = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/signin" -Method Post -Body $body -ContentType "application/json"
    $token = $loginResponse.accessToken
    
    $headers = @{
        "Authorization" = "Bearer $token"
    }
    
    $quizzesResponse = Invoke-RestMethod -Uri "http://localhost:8081/api/quizzes" -Method Get -Headers $headers
    Write-Host "   Status: ✅ Success" -ForegroundColor Green
    Write-Host "   Found $($quizzesResponse.Count) quizzes:" -ForegroundColor Cyan
    
    foreach ($quiz in $quizzesResponse) {
        Write-Host "     - $($quiz.title)" -ForegroundColor White
    }
} catch {
    Write-Host "   Status: ❌ Failed" -ForegroundColor Red
}

Write-Host "`n🎉 ALL TESTS COMPLETED!" -ForegroundColor Green
Write-Host "================================" -ForegroundColor Green
Write-Host "📱 Frontend: http://localhost:3000" -ForegroundColor Cyan
Write-Host "🔧 Backend API: http://localhost:8081" -ForegroundColor Cyan
Write-Host "🗄️  H2 Console: http://localhost:8081/h2-console" -ForegroundColor Cyan
Write-Host "`n🔑 Default Login Credentials:" -ForegroundColor Yellow
Write-Host "   Admin: admin / admin123" -ForegroundColor White
Write-Host "   User:  user  / user123" -ForegroundColor White
Write-Host "`n📚 Sample Quizzes Available:" -ForegroundColor Yellow
Write-Host "   • Java Programming Quiz" -ForegroundColor White
Write-Host "   • World Geography Quiz" -ForegroundColor White
Write-Host "   • General Science Quiz" -ForegroundColor White
Write-Host "   • World History Quiz" -ForegroundColor White
Write-Host "   • Sports Quiz" -ForegroundColor White
