# Test Quiz API
Write-Host "Testing Quiz API..." -ForegroundColor Green

# Login first
$body = @{
    username = "user"
    password = "user123"
} | ConvertTo-Json

$loginResponse = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/signin" -Method Post -Body $body -ContentType "application/json"
$token = $loginResponse.accessToken

Write-Host "Login successful!" -ForegroundColor Green

# Get quizzes
$headers = @{
    "Authorization" = "Bearer $token"
}

$quizzesResponse = Invoke-RestMethod -Uri "http://localhost:8081/api/quizzes" -Method Get -Headers $headers

Write-Host "Found $($quizzesResponse.Count) quizzes:" -ForegroundColor Cyan
foreach ($quiz in $quizzesResponse) {
    Write-Host "- $($quiz.title)" -ForegroundColor White
    Write-Host "  Description: $($quiz.description)" -ForegroundColor Gray
}
