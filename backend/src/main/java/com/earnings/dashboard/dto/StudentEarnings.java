package com.earnings.dashboard.dto;

import java.math.BigDecimal;

public class StudentEarnings {
    private Long studentId;
    private String firstName;
    private String lastName;
    private Integer grade;
    private String subject;
    private String country;
    private String contactNumber;
    private String registrationId;
    private BigDecimal totalEarnings;
    private Integer totalSessions;
    
    // Constructors
    public StudentEarnings() {}
    
    public StudentEarnings(Long studentId, String firstName, String lastName, Integer grade, 
                          String subject, String country, String contactNumber, String registrationId,
                          BigDecimal totalEarnings, Integer totalSessions) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.grade = grade;
        this.subject = subject;
        this.country = country;
        this.contactNumber = contactNumber;
        this.registrationId = registrationId;
        this.totalEarnings = totalEarnings;
        this.totalSessions = totalSessions;
    }
    
    // Getters and Setters
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public Integer getGrade() { return grade; }
    public void setGrade(Integer grade) { this.grade = grade; }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    
    public String getRegistrationId() { return registrationId; }
    public void setRegistrationId(String registrationId) { this.registrationId = registrationId; }
    
    public BigDecimal getTotalEarnings() { return totalEarnings; }
    public void setTotalEarnings(BigDecimal totalEarnings) { this.totalEarnings = totalEarnings; }
    
    public Integer getTotalSessions() { return totalSessions; }
    public void setTotalSessions(Integer totalSessions) { this.totalSessions = totalSessions; }
}
