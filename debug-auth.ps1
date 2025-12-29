# Debug Authentication Issues
Write-Host "Debugging Quiz Application Authentication..." -ForegroundColor Green

# Test 1: Check if backend is running
Write-Host "`n1. Testing Backend Health..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8081/api/auth/test" -Method Get -TimeoutSec 5
    Write-Host "✅ Backend is responding" -ForegroundColor Green
} catch {
    Write-Host "❌ Backend not responding: $($_.Exception.Message)" -ForegroundColor Red
    exit
}

# Test 2: Test Registration with detailed error info
Write-Host "`n2. Testing Registration..." -ForegroundColor Yellow
try {
    $body = @{
        username = "debuguser"
        email = "debug@example.com"
        password = "debugpass123"
    } | ConvertTo-Json
    
    $response = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/signup" -Method Post -Body $body -ContentType "application/json"
    Write-Host "✅ Registration successful: $($response.success)" -ForegroundColor Green
    Write-Host "Message: $($response.message)" -ForegroundColor Cyan
} catch {
    Write-Host "❌ Registration failed" -ForegroundColor Red
    Write-Host "Status: $($_.Exception.Response.StatusCode.value__)" -ForegroundColor Yellow
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Yellow
    if ($_.Exception.Response) {
        $stream = $_.Exception.Response.GetResponseStream()
        $reader = New-Object System.IO.StreamReader($stream)
        $reader.BaseStream.Position = 0
        $errorBody = $reader.ReadToEnd()
        Write-Host "Error Body: $errorBody" -ForegroundColor Red
    }
}

# Test 3: Test Login with detailed error info
Write-Host "`n3. Testing Login with existing user..." -ForegroundColor Yellow
try {
    $body = @{
        username = "user"
        password = "user123"
    } | ConvertTo-Json
    
    $response = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/signin" -Method Post -Body $body -ContentType "application/json"
    Write-Host "✅ Login successful" -ForegroundColor Green
    Write-Host "Token: $($response.accessToken.Substring(0, 20))..." -ForegroundColor Cyan
    if ($response.user) {
        Write-Host "User: $($response.user.username) (Role: $($response.user.role))" -ForegroundColor Cyan
    }
} catch {
    Write-Host "❌ Login failed" -ForegroundColor Red
    Write-Host "Status: $($_.Exception.Response.StatusCode.value__)" -ForegroundColor Yellow
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Yellow
    if ($_.Exception.Response) {
        $stream = $_.Exception.Response.GetResponseStream()
        $reader = New-Object System.IO.StreamReader($stream)
        $reader.BaseStream.Position = 0
        $errorBody = $reader.ReadToEnd()
        Write-Host "Error Body: $errorBody" -ForegroundColor Red
    }
}

# Test 4: Test Login with admin
Write-Host "`n4. Testing Login with admin..." -ForegroundColor Yellow
try {
    $body = @{
        username = "admin"
        password = "admin123"
    } | ConvertTo-Json
    
    $response = Invoke-RestMethod -Uri "http://localhost:8081/api/auth/signin" -Method Post -Body $body -ContentType "application/json"
    Write-Host "✅ Admin login successful" -ForegroundColor Green
    Write-Host "Token: $($response.accessToken.Substring(0, 20))..." -ForegroundColor Cyan
    if ($response.user) {
        Write-Host "User: $($response.user.username) (Role: $($response.user.role))" -ForegroundColor Cyan
    }
} catch {
    Write-Host "❌ Admin login failed" -ForegroundColor Red
    Write-Host "Status: $($_.Exception.Response.StatusCode.value__)" -ForegroundColor Yellow
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Yellow
    if ($_.Exception.Response) {
        $stream = $_.Exception.Response.GetResponseStream()
        $reader = New-Object System.IO.StreamReader($stream)
        $reader.BaseStream.Position = 0
        $errorBody = $reader.ReadToEnd()
        Write-Host "Error Body: $errorBody" -ForegroundColor Red
    }
}

Write-Host "`nDebug Complete!" -ForegroundColor Green
