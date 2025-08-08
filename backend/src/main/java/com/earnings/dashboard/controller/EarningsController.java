package com.earnings.dashboard.controller;

import com.earnings.dashboard.dto.EarningsSummary;
import com.earnings.dashboard.dto.StudentEarnings;
import com.earnings.dashboard.dto.UpcomingSession;
import com.earnings.dashboard.service.EarningsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/earnings")
@CrossOrigin(origins = "*")
public class EarningsController {
    
    private static final Logger logger = LoggerFactory.getLogger(EarningsController.class);
    
    @Autowired
    private EarningsService earningsService;
    
    @GetMapping("/monthly-breakdown")
    public ResponseEntity<Map<String, BigDecimal>> getMonthlyBreakdown(
            @RequestParam(required = false) Long studentId,
            Authentication authentication) {
        try {
            String username = authentication.getName();
            Map<String, BigDecimal> monthlyBreakdown = earningsService.getMonthlyBreakdown(username, studentId);
            return ResponseEntity.ok(monthlyBreakdown);
        } catch (Exception e) {
            logger.error("Error getting monthly breakdown for user: {}", authentication.getName(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/summary")
    public ResponseEntity<EarningsSummary> getEarningsSummary(
            Authentication authentication,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {
        
        EarningsSummary summary = earningsService.getEarningsSummary(authentication.getName(), studentId, month, year);
        return ResponseEntity.ok(summary);
    }
    
    @GetMapping("/students")
    public ResponseEntity<List<StudentEarnings>> getStudentEarnings(Authentication authentication) {
        List<StudentEarnings> studentEarnings = earningsService.getStudentEarnings(authentication.getName());
        return ResponseEntity.ok(studentEarnings);
    }
    
    @GetMapping("/upcoming-sessions")
    public ResponseEntity<List<UpcomingSession>> getUpcomingSessions(
            Authentication authentication,
            @RequestParam(required = false) Long studentId) {
        
        List<UpcomingSession> sessions = earningsService.getUpcomingSessions(authentication.getName(), studentId);
        return ResponseEntity.ok(sessions);
    }
}
