package com.earnings.dashboard.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDateTime sessionDateTime;
    
    @Column(nullable = false)
    private Integer durationMinutes;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal hourlyRate;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalEarnings;
    
    @Column(nullable = false)
    private Boolean isCompleted;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    // Constructors
    public Session() {}
    
    public Session(LocalDateTime sessionDateTime, Integer durationMinutes, BigDecimal hourlyRate,
                  Boolean isCompleted, Teacher teacher, Student student) {
        this.sessionDateTime = sessionDateTime;
        this.durationMinutes = durationMinutes;
        this.hourlyRate = hourlyRate;
        this.isCompleted = isCompleted;
        this.teacher = teacher;
        this.student = student;
        this.totalEarnings = calculateEarnings();
    }
    
    private BigDecimal calculateEarnings() {
        if (hourlyRate != null && durationMinutes != null) {
            return hourlyRate.multiply(BigDecimal.valueOf(durationMinutes))
                           .divide(BigDecimal.valueOf(60), 2, BigDecimal.ROUND_HALF_UP);
        }
        return BigDecimal.ZERO;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public LocalDateTime getSessionDateTime() { return sessionDateTime; }
    public void setSessionDateTime(LocalDateTime sessionDateTime) { this.sessionDateTime = sessionDateTime; }
    
    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { 
        this.durationMinutes = durationMinutes;
        this.totalEarnings = calculateEarnings();
    }
    
    public BigDecimal getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(BigDecimal hourlyRate) { 
        this.hourlyRate = hourlyRate;
        this.totalEarnings = calculateEarnings();
    }
    
    public BigDecimal getTotalEarnings() { return totalEarnings; }
    public void setTotalEarnings(BigDecimal totalEarnings) { this.totalEarnings = totalEarnings; }
    
    public Boolean getIsCompleted() { return isCompleted; }
    public void setIsCompleted(Boolean isCompleted) { this.isCompleted = isCompleted; }
    
    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }
    
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
}
