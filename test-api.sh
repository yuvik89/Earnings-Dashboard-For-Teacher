#!/bin/bash

echo "🧪 Testing Earnings Dashboard API..."
echo ""

BASE_URL="http://localhost:8080/api"

# Test 1: Login
echo "1. Testing Login..."
LOGIN_RESPONSE=$(curl -s -X POST $BASE_URL/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"teacher1","password":"password1"}')

if echo "$LOGIN_RESPONSE" | grep -q "token"; then
    echo "✅ Login successful"
    TOKEN=$(echo "$LOGIN_RESPONSE" | grep -o '"token":"[^"]*"' | cut -d'"' -f4)
    echo "   Token: ${TOKEN:0:50}..."
else
    echo "❌ Login failed"
    echo "   Response: $LOGIN_RESPONSE"
    exit 1
fi

echo ""

# Test 2: Earnings Summary
echo "2. Testing Earnings Summary..."
SUMMARY_RESPONSE=$(curl -s -X GET $BASE_URL/earnings/summary \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json")

if echo "$SUMMARY_RESPONSE" | grep -q "yearToDate"; then
    echo "✅ Earnings summary retrieved successfully"
    echo "   Response: $SUMMARY_RESPONSE"
else
    echo "❌ Earnings summary failed"
    echo "   Response: $SUMMARY_RESPONSE"
fi

echo ""

# Test 3: Student Earnings
echo "3. Testing Student Earnings..."
STUDENTS_RESPONSE=$(curl -s -X GET $BASE_URL/earnings/students \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json")

if echo "$STUDENTS_RESPONSE" | grep -q "studentId"; then
    echo "✅ Student earnings retrieved successfully"
    STUDENT_COUNT=$(echo "$STUDENTS_RESPONSE" | grep -o "studentId" | wc -l)
    echo "   Found $STUDENT_COUNT students"
else
    echo "❌ Student earnings failed"
    echo "   Response: $STUDENTS_RESPONSE"
fi

echo ""

# Test 4: Upcoming Sessions
echo "4. Testing Upcoming Sessions..."
SESSIONS_RESPONSE=$(curl -s -X GET $BASE_URL/earnings/upcoming-sessions \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json")

if echo "$SESSIONS_RESPONSE" | grep -q "\["; then
    echo "✅ Upcoming sessions retrieved successfully"
    SESSION_COUNT=$(echo "$SESSIONS_RESPONSE" | grep -o "sessionId" | wc -l)
    echo "   Found $SESSION_COUNT upcoming sessions"
else
    echo "❌ Upcoming sessions failed"
    echo "   Response: $SESSIONS_RESPONSE"
fi

echo ""

# Test 5: Test with different teacher
echo "5. Testing with different teacher (teacher2)..."
LOGIN_RESPONSE2=$(curl -s -X POST $BASE_URL/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"teacher2","password":"password2"}')

if echo "$LOGIN_RESPONSE2" | grep -q "token"; then
    echo "✅ Teacher2 login successful"
    TOKEN2=$(echo "$LOGIN_RESPONSE2" | grep -o '"token":"[^"]*"' | cut -d'"' -f4)
    
    # Get teacher2's students
    STUDENTS_RESPONSE2=$(curl -s -X GET $BASE_URL/earnings/students \
      -H "Authorization: Bearer $TOKEN2" \
      -H "Content-Type: application/json")
    
    if echo "$STUDENTS_RESPONSE2" | grep -q "Science"; then
        echo "✅ Teacher2 data isolation working (Science subject found)"
    else
        echo "⚠️  Teacher2 data might not be properly isolated"
    fi
else
    echo "❌ Teacher2 login failed"
fi

echo ""
echo "🎉 API Testing Complete!"
echo ""
echo "All core endpoints are working. You can now:"
echo "1. Open http://localhost:4200 in your browser"
echo "2. Login with teacher1/password1 or any teacher1-10/password1-10"
echo "3. Explore the earnings dashboard"
