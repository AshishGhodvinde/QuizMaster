# Quiz Application Local Startup Script
Write-Host "Starting Quiz Application locally..." -ForegroundColor Green

# Start Backend
Write-Host "Starting backend server..." -ForegroundColor Yellow
Set-Location backend
Start-Process -WindowStyle Hidden powershell -ArgumentList "-Command", "mvn clean package -DskipTests; java -jar target/quiz-application-0.0.1-SNAPSHOT.jar"

# Wait for backend to start
Write-Host "Waiting for backend to start..." -ForegroundColor Yellow
Start-Sleep -Seconds 15

# Start Frontend
Write-Host "Starting frontend server..." -ForegroundColor Yellow
Set-Location ../frontend/quiz-app
Start-Process powershell -ArgumentList "-Command", "npm run dev"

# Wait for frontend to start
Write-Host "Waiting for frontend to start..." -ForegroundColor Yellow
Start-Sleep -Seconds 5

Write-Host "Application is now running!" -ForegroundColor Green
Write-Host "Frontend: http://localhost:3000" -ForegroundColor Cyan
Write-Host "Backend API: http://localhost:8081" -ForegroundColor Cyan
Write-Host "H2 Console: http://localhost:8081/h2-console" -ForegroundColor Cyan
Write-Host ""
Write-Host "Default Login Credentials:" -ForegroundColor Yellow
Write-Host "Username: admin" -ForegroundColor White
Write-Host "Password: admin123" -ForegroundColor White
Write-Host ""
Write-Host "Press any key to stop all servers..." -ForegroundColor Red

# Wait for user input to stop
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")

# Stop all processes
Write-Host "Stopping servers..." -ForegroundColor Yellow
Get-Process -Name java -ErrorAction SilentlyContinue | Stop-Process -Force
Get-Process -Name node -ErrorAction SilentlyContinue | Stop-Process -Force

Write-Host "All servers stopped!" -ForegroundColor Red
