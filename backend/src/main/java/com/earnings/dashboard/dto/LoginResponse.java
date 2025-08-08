package com.earnings.dashboard.dto;

public class LoginResponse {
    private String token;
    private String username;
    private String firstName;
    private String lastName;
    private String subject;
    
    // Constructors
    public LoginResponse() {}
    
    public LoginResponse(String token, String username, String firstName, String lastName, String subject) {
        this.token = token;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;
    }
    
    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
}
