package com.earnings.dashboard.security;

import com.earnings.dashboard.entity.Teacher;
import com.earnings.dashboard.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private TeacherRepository teacherRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Teacher teacher = teacherRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Teacher not found: " + username));
        
        return User.builder()
                .username(teacher.getUsername())
                .password(teacher.getPassword())
                .authorities(new ArrayList<>())
                .build();
    }
}
