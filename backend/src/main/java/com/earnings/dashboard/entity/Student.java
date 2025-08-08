package com.earnings.dashboard.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false)
    private Integer grade;
    
    @Column(nullable = false)
    private String subject;
    
    @Column(nullable = false)
    private String country;
    
    @Column(nullable = false)
    private String contactNumber;
    
    @Column(unique = true, nullable = false)
    private String registrationId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Session> sessions;
    
    // Constructors
    public Student() {}
    
    public Student(String firstName, String lastName, Integer grade, String subject, 
                  String country, String contactNumber, String registrationId, Teacher teacher) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.grade = grade;
        this.subject = subject;
        this.country = country;
        this.contactNumber = contactNumber;
        this.registrationId = registrationId;
        this.teacher = teacher;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
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
    
    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }
    
    public List<Session> getSessions() { return sessions; }
    public void setSessions(List<Session> sessions) { this.sessions = sessions; }
}
