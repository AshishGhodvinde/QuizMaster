# Test login with newly created user
Write-Host "Testing login with test user..." -ForegroundColor Green

try {
    $body = @{
        username = "testpass"
        password = "password123"
    } | ConvertTo-Json
    
    $response = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/signin" -Method Post -Body $body -ContentType "application/json"
    Write-Host "✅ Login successful!" -ForegroundColor Green
    Write-Host "Token: $($response.accessToken.Substring(0, 20))..." -ForegroundColor Cyan
    Write-Host "User: $($response.user.username) (Role: $($response.user.role))" -ForegroundColor Cyan
} catch {
    Write-Host "❌ Login failed" -ForegroundColor Red
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Yellow
    if ($_.Exception.Response) {
        $stream = $_.Exception.Response.GetResponseStream()
        $reader = New-Object System.IO.StreamReader($stream)
        $reader.BaseStream.Position = 0
        $errorBody = $reader.ReadToEnd()
        Write-Host "Error Body: $errorBody" -ForegroundColor Red
    }
}
