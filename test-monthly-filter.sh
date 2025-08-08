#!/bin/bash

echo "=== Testing Monthly Filter Functionality ==="
echo ""

# Login and get token
echo "1. Logging in as teacher1..."
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "teacher1", "password": "password1"}' \
  -s > login_response.json

TOKEN=$(grep -o '"token":"[^"]*"' login_response.json | cut -d'"' -f4)

if [ -z "$TOKEN" ]; then
    echo "❌ Login failed"
    exit 1
fi

echo "✅ Login successful"
echo ""

# Test current period (no filter)
echo "2. Testing current period (no month/year filter):"
curl -H "Authorization: Bearer $TOKEN" \
  "http://localhost:8080/api/earnings/summary" \
  -s | python3 -m json.tool
echo ""

# Test specific month filter - August 2025
echo "3. Testing monthly filter for August 2025:"
curl -H "Authorization: Bearer $TOKEN" \
  "http://localhost:8080/api/earnings/summary?month=8&year=2025" \
  -s | python3 -m json.tool
echo ""

# Test specific month filter - January 2025 (should be 0)
echo "4. Testing monthly filter for January 2025 (should be 0):"
curl -H "Authorization: Bearer $TOKEN" \
  "http://localhost:8080/api/earnings/summary?month=1&year=2025" \
  -s | python3 -m json.tool
echo ""

# Test with student filter + month filter
echo "5. Testing monthly filter with student filter (Student ID 1, August 2025):"
curl -H "Authorization: Bearer $TOKEN" \
  "http://localhost:8080/api/earnings/summary?month=8&year=2025&studentId=1" \
  -s | python3 -m json.tool
echo ""

echo "=== Monthly Filter Test Complete ==="
echo ""
echo "🌐 Frontend is running at: http://localhost:4200"
echo "🔧 Backend is running at: http://localhost:8080"
echo ""
echo "Login credentials for testing:"
echo "Username: teacher1, Password: password1"
echo "Username: teacher2, Password: password2"
echo "... (teacher1 to teacher10 with password1 to password10)"

# Cleanup
rm -f login_response.json
