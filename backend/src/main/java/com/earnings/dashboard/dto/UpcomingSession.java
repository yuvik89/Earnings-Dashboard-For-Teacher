package com.earnings.dashboard.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class UpcomingSession {
    private Long sessionId;
    private Long studentId;
    private String studentName;
    private LocalDateTime sessionDateTime;
    private Integer durationMinutes;
    private BigDecimal expectedEarnings;
    private String subject;
    
    // Constructors
    public UpcomingSession() {}
    
    public UpcomingSession(Long sessionId, Long studentId, String studentName, 
                          LocalDateTime sessionDateTime, Integer durationMinutes, 
                          BigDecimal expectedEarnings, String subject) {
        this.sessionId = sessionId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.sessionDateTime = sessionDateTime;
        this.durationMinutes = durationMinutes;
        this.expectedEarnings = expectedEarnings;
        this.subject = subject;
    }
    
    // Getters and Setters
    public Long getSessionId() { return sessionId; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }
    
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    
    public LocalDateTime getSessionDateTime() { return sessionDateTime; }
    public void setSessionDateTime(LocalDateTime sessionDateTime) { this.sessionDateTime = sessionDateTime; }
    
    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
    
    public BigDecimal getExpectedEarnings() { return expectedEarnings; }
    public void setExpectedEarnings(BigDecimal expectedEarnings) { this.expectedEarnings = expectedEarnings; }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
}
