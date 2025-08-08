#!/bin/bash

echo "Starting Earnings Dashboard Application..."

# Function to check if a port is in use
check_port() {
    lsof -i :$1 > /dev/null 2>&1
    return $?
}

# Start backend
echo "Starting Spring Boot backend..."
cd backend
if check_port 8080; then
    echo "Port 8080 is already in use. Backend might already be running."
else
    mvn spring-boot:run > backend.log 2>&1 &
    BACKEND_PID=$!
    echo "Backend started with PID: $BACKEND_PID"
fi

# Wait for backend to start
echo "Waiting for backend to start..."
sleep 15

# Test backend
echo "Testing backend..."
RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/api/h2-console)
if [ "$RESPONSE" = "302" ]; then
    echo "✅ Backend is running successfully on http://localhost:8080"
else
    echo "❌ Backend failed to start properly"
    exit 1
fi

# Start frontend
echo "Starting Angular frontend..."
cd ../frontend
if check_port 4200; then
    echo "Port 4200 is already in use. Frontend might already be running."
else
    npm start > frontend.log 2>&1 &
    FRONTEND_PID=$!
    echo "Frontend started with PID: $FRONTEND_PID"
fi

# Wait for frontend to start
echo "Waiting for frontend to start..."
sleep 10

# Test frontend
echo "Testing frontend..."
RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:4200)
if [ "$RESPONSE" = "200" ]; then
    echo "✅ Frontend is running successfully on http://localhost:4200"
else
    echo "❌ Frontend failed to start properly"
fi

echo ""
echo "🎉 Earnings Dashboard Application is ready!"
echo ""
echo "📱 Frontend: http://localhost:4200"
echo "🔧 Backend API: http://localhost:8080/api"
echo "🗄️  H2 Database Console: http://localhost:8080/api/h2-console"
echo ""
echo "Demo Login Credentials:"
echo "Username: teacher1 to teacher10"
echo "Password: password1 to password10"
echo ""
echo "Example: Username: teacher1, Password: password1"
echo ""
echo "To stop the application, run: ./stop.sh"
