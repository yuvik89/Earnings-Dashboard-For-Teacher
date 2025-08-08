#!/bin/bash

echo "Stopping Earnings Dashboard Application..."

# Stop Spring Boot backend
echo "Stopping backend..."
pkill -f "spring-boot:run"
if [ $? -eq 0 ]; then
    echo "✅ Backend stopped"
else
    echo "ℹ️  No backend process found"
fi

# Stop Angular frontend
echo "Stopping frontend..."
pkill -f "npm start"
if [ $? -eq 0 ]; then
    echo "✅ Frontend stopped"
else
    echo "ℹ️  No frontend process found"
fi

echo "🛑 Earnings Dashboard Application stopped!"
