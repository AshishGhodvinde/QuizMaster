# Check users in database via H2 console
Write-Host "Checking users in H2 database..." -ForegroundColor Green

# First, let's check if we can access the H2 console
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8081/h2-console" -Method Get -TimeoutSec 5
    Write-Host "✅ H2 console is accessible at http://localhost:8081/h2-console" -ForegroundColor Green
    Write-Host "JDBC URL: jdbc:h2:mem:testdb" -ForegroundColor Cyan
    Write-Host "Username: sa" -ForegroundColor Cyan
    Write-Host "Password: password" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Run this SQL query to check users:" -ForegroundColor Yellow
    Write-Host "SELECT username, password, role FROM users;" -ForegroundColor White
} catch {
    Write-Host "❌ H2 console not accessible" -ForegroundColor Red
}

# Let's also try to create a new user and immediately try to login
Write-Host "`nTesting create and login immediately..." -ForegroundColor Yellow

try {
    # Create user
    $createBody = @{
        username = "testuser2"
        email = "test2@example.com"
        password = "testpass123"
    } | ConvertTo-Json
    
    $createResponse = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/signup" -Method Post -Body $createBody -ContentType "application/json"
    Write-Host "✅ User created: $($createResponse.success)" -ForegroundColor Green
    
    # Try to login immediately
    $loginBody = @{
        username = "testuser2"
        password = "testpass123"
    } | ConvertTo-Json
    
    $loginResponse = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/signin" -Method Post -Body $loginBody -ContentType "application/json"
    Write-Host "✅ Immediate login successful!" -ForegroundColor Green
    Write-Host "Token: $($loginResponse.accessToken.Substring(0, 20))..." -ForegroundColor Cyan
} catch {
    Write-Host "❌ Immediate login failed" -ForegroundColor Red
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Yellow
    if ($_.Exception.Response) {
        $stream = $_.Exception.Response.GetResponseStream()
        $reader = New-Object System.IO.StreamReader($stream)
        $reader.BaseStream.Position = 0
        $errorBody = $reader.ReadToEnd()
        Write-Host "Error Body: $errorBody" -ForegroundColor Red
    }
}
