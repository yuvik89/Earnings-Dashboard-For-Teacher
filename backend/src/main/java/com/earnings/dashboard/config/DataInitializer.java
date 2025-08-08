package com.earnings.dashboard.config;

import com.earnings.dashboard.entity.Session;
import com.earnings.dashboard.entity.Student;
import com.earnings.dashboard.entity.Teacher;
import com.earnings.dashboard.repository.SessionRepository;
import com.earnings.dashboard.repository.StudentRepository;
import com.earnings.dashboard.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private TeacherRepository teacherRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private SessionRepository sessionRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    private final Random random = new Random();
    
    @Override
    public void run(String... args) throws Exception {
        if (teacherRepository.count() == 0) {
            initializeData();
        }
    }
    
    private void initializeData() {
        // Create 10 teachers
        List<String> subjects = Arrays.asList("Mathematics", "Science", "English", "History", "Physics", 
                                            "Chemistry", "Biology", "Geography", "Art", "Music");
        
        List<String> countries = Arrays.asList("USA", "Canada", "UK", "Australia", "Germany", "France", "Japan", "India");
        
        for (int i = 1; i <= 10; i++) {
            Teacher teacher = new Teacher(
                "teacher" + i,
                passwordEncoder.encode("password" + i),
                "Teacher" + i,
                "Last" + i,
                "teacher" + i + "@example.com",
                subjects.get(i - 1)
            );
            teacher = teacherRepository.save(teacher);
            
            // Create 4-6 students for each teacher
            int studentCount = 4 + random.nextInt(3); // 4-6 students
            for (int j = 1; j <= studentCount; j++) {
                int grade = 5 + random.nextInt(8); // Grades 5-12
                Student student = new Student(
                    "Student" + i + j,
                    "LastName" + i + j,
                    grade,
                    subjects.get(i - 1),
                    countries.get(random.nextInt(countries.size())),
                    "+1-555-" + String.format("%04d", random.nextInt(10000)),
                    "REG" + i + String.format("%03d", j),
                    teacher
                );
                student = studentRepository.save(student);
                
                // Create sessions for each student
                createSessionsForStudent(teacher, student, grade);
            }
        }
    }
    
    private void createSessionsForStudent(Teacher teacher, Student student, int grade) {
        BigDecimal hourlyRate = getHourlyRateByGrade(grade);
        
        // Create sessions for 2025 Jan-Aug (completed) - August is current month
        
        // January to August 2025 - Completed sessions
        for (int month = 1; month <= 8; month++) {
            int sessionsPerMonth = 3 + random.nextInt(5); // 3-7 sessions per month
            
            for (int session = 0; session < sessionsPerMonth; session++) {
                LocalDateTime sessionTime = LocalDateTime.of(2025, month, 
                    1 + random.nextInt(28), // Day 1-28 to avoid month-end issues
                    9 + random.nextInt(10), // Hours 9-18 (9 AM to 6 PM)
                    random.nextInt(60), // Random minutes
                    0);
                
                int duration = 45 + random.nextInt(76); // 45-120 minutes
                
                Session completedSession = new Session(
                    sessionTime,
                    duration,
                    hourlyRate,
                    true, // completed
                    teacher,
                    student
                );
                sessionRepository.save(completedSession);
            }
        }
        
        // August 2025 - Upcoming sessions (rest of August)
        int upcomingSessions = 3 + random.nextInt(4); // 3-6 upcoming sessions
        for (int session = 0; session < upcomingSessions; session++) {
            LocalDateTime sessionTime = LocalDateTime.of(2025, 8,
                7 + random.nextInt(24), // Day 7-30 (rest of August)
                9 + random.nextInt(10), // Hours 9-18
                random.nextInt(60), // Random minutes
                0);
            
            int duration = 45 + random.nextInt(76); // 45-120 minutes
            
            Session upcomingSession = new Session(
                sessionTime,
                duration,
                hourlyRate,
                false, // not completed (upcoming)
                teacher,
                student
            );
            sessionRepository.save(upcomingSession);
        }
    }
    
    private BigDecimal getHourlyRateByGrade(int grade) {
        // Grade-based hourly rates
        switch (grade) {
            case 5: case 6: return new BigDecimal("10.00");
            case 7: case 8: return new BigDecimal("12.00");
            case 9: case 10: return new BigDecimal("15.00");
            case 11: case 12: return new BigDecimal("18.00");
            default: return new BigDecimal("10.00");
        }
    }
}
