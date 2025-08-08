package com.earnings.dashboard.service;

import com.earnings.dashboard.dto.LoginRequest;
import com.earnings.dashboard.dto.LoginResponse;
import com.earnings.dashboard.entity.Teacher;
import com.earnings.dashboard.repository.TeacherRepository;
import com.earnings.dashboard.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private TeacherRepository teacherRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    public LoginResponse login(LoginRequest loginRequest) {
        Teacher teacher = teacherRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));
        
        if (!passwordEncoder.matches(loginRequest.getPassword(), teacher.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }
        
        String token = jwtUtil.generateToken(teacher.getUsername());
        
        return new LoginResponse(token, teacher.getUsername(), teacher.getFirstName(), 
                               teacher.getLastName(), teacher.getSubject());
    }
}
