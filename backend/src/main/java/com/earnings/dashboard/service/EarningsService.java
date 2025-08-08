package com.earnings.dashboard.service;

import com.earnings.dashboard.dto.EarningsSummary;
import com.earnings.dashboard.dto.StudentEarnings;
import com.earnings.dashboard.dto.UpcomingSession;
import com.earnings.dashboard.entity.Session;
import com.earnings.dashboard.entity.Student;
import com.earnings.dashboard.entity.Teacher;
import com.earnings.dashboard.repository.SessionRepository;
import com.earnings.dashboard.repository.StudentRepository;
import com.earnings.dashboard.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EarningsService {
    
    @Autowired
    private TeacherRepository teacherRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private SessionRepository sessionRepository;
    
    public Teacher getTeacherByUsername(String username) {
        return teacherRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
    }
    
    public Map<String, BigDecimal> getMonthlyBreakdown(String username, Long studentId) {
        Teacher teacher = getTeacherByUsername(username);
        Map<String, BigDecimal> monthlyEarnings = new LinkedHashMap<>();
        LocalDateTime now = LocalDateTime.now();
        
        // Generate monthly breakdown for Jan-Aug 2025 (August is current month)
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August"};
        
        for (int month = 1; month <= 8; month++) {
            LocalDateTime startOfMonth = LocalDateTime.of(2025, month, 1, 0, 0, 0);
            LocalDateTime endOfMonth;
            
            // For current month (August), use current date/time instead of end of month
            if (month == now.getMonthValue() && 2025 == now.getYear()) {
                endOfMonth = now;
            } else {
                endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);
            }
            
            BigDecimal monthEarnings = getEarningsForPeriod(teacher.getId(), studentId, startOfMonth, endOfMonth);
            monthlyEarnings.put(months[month - 1], monthEarnings);
        }
        
        return monthlyEarnings;
    }
    
    public EarningsSummary getEarningsSummary(String username, Long studentId) {
        return getEarningsSummary(username, studentId, null, null);
    }
    
    public EarningsSummary getEarningsSummary(String username, Long studentId, Integer month, Integer year) {
        Teacher teacher = getTeacherByUsername(username);
        LocalDateTime now = LocalDateTime.now();
        
        // If specific month/year is provided, calculate for that month
        if (month != null && year != null) {
            LocalDateTime startOfSpecificMonth = LocalDateTime.of(year, month, 1, 0, 0, 0);
            LocalDateTime endOfSpecificMonth = startOfSpecificMonth.plusMonths(1).minusSeconds(1);
            BigDecimal specificMonthEarnings = getEarningsForPeriod(teacher.getId(), studentId, startOfSpecificMonth, endOfSpecificMonth);
            
            // For specific month view, show the month earnings in all three fields for consistency
            return new EarningsSummary(specificMonthEarnings, specificMonthEarnings, specificMonthEarnings, 
                                     year + "-" + String.format("%02d", month));
        }
        
        // Default behavior - current period calculations
        // Year to date
        LocalDateTime startOfYear = now.withDayOfYear(1).withHour(0).withMinute(0).withSecond(0);
        BigDecimal ytdEarnings = getEarningsForPeriod(teacher.getId(), studentId, startOfYear, now);
        
        // Monthly (current month)
        LocalDateTime startOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        BigDecimal monthlyEarnings = getEarningsForPeriod(teacher.getId(), studentId, startOfMonth, now);
        
        // Weekly
        LocalDateTime startOfWeek = now.minus(7, ChronoUnit.DAYS);
        BigDecimal weeklyEarnings = getEarningsForPeriod(teacher.getId(), studentId, startOfWeek, now);
        
        return new EarningsSummary(ytdEarnings, monthlyEarnings, weeklyEarnings, 
                                 now.getYear() + "-" + String.format("%02d", now.getMonthValue()));
    }
    
    private BigDecimal getEarningsForPeriod(Long teacherId, Long studentId, LocalDateTime start, LocalDateTime end) {
        BigDecimal earnings;
        if (studentId != null) {
            earnings = sessionRepository.getTotalEarningsByTeacherStudentAndDateRange(teacherId, studentId, start, end);
        } else {
            earnings = sessionRepository.getTotalEarningsByTeacherAndDateRange(teacherId, start, end);
        }
        return earnings != null ? earnings : BigDecimal.ZERO;
    }
    
    public List<StudentEarnings> getStudentEarnings(String username) {
        Teacher teacher = getTeacherByUsername(username);
        List<Student> students = studentRepository.findByTeacherId(teacher.getId());
        
        return students.stream().map(student -> {
            LocalDateTime startOfYear = LocalDateTime.now().withDayOfYear(1).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime now = LocalDateTime.now();
            
            BigDecimal totalEarnings = getEarningsForPeriod(teacher.getId(), student.getId(), startOfYear, now);
            List<Session> sessions = sessionRepository.findCompletedSessionsByTeacherStudentAndDateRange(
                    teacher.getId(), student.getId(), startOfYear, now);
            
            return new StudentEarnings(
                    student.getId(),
                    student.getFirstName(),
                    student.getLastName(),
                    student.getGrade(),
                    student.getSubject(),
                    student.getCountry(),
                    student.getContactNumber(),
                    student.getRegistrationId(),
                    totalEarnings,
                    sessions.size()
            );
        }).collect(Collectors.toList());
    }
    
    public List<UpcomingSession> getUpcomingSessions(String username, Long studentId) {
        Teacher teacher = getTeacherByUsername(username);
        List<Session> sessions;
        
        if (studentId != null) {
            sessions = sessionRepository.findByTeacherIdAndStudentIdAndIsCompleted(teacher.getId(), studentId, false);
        } else {
            sessions = sessionRepository.findByTeacherIdAndIsCompleted(teacher.getId(), false);
        }
        
        return sessions.stream()
                .filter(session -> session.getSessionDateTime().isAfter(LocalDateTime.now()))
                .map(session -> new UpcomingSession(
                        session.getId(),
                        session.getStudent().getId(),
                        session.getStudent().getFirstName() + " " + session.getStudent().getLastName(),
                        session.getSessionDateTime(),
                        session.getDurationMinutes(),
                        session.getTotalEarnings(),
                        session.getStudent().getSubject()
                ))
                .collect(Collectors.toList());
    }
}
