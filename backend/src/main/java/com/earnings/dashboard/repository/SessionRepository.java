package com.earnings.dashboard.repository;

import com.earnings.dashboard.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    
    List<Session> findByTeacherIdAndIsCompleted(Long teacherId, Boolean isCompleted);
    
    List<Session> findByTeacherIdAndStudentIdAndIsCompleted(Long teacherId, Long studentId, Boolean isCompleted);
    
    @Query("SELECT s FROM Session s WHERE s.teacher.id = :teacherId AND s.sessionDateTime >= :startDate AND s.sessionDateTime <= :endDate AND s.isCompleted = true")
    List<Session> findCompletedSessionsByTeacherAndDateRange(@Param("teacherId") Long teacherId, 
                                                           @Param("startDate") LocalDateTime startDate, 
                                                           @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT s FROM Session s WHERE s.teacher.id = :teacherId AND s.student.id = :studentId AND s.sessionDateTime >= :startDate AND s.sessionDateTime <= :endDate AND s.isCompleted = true")
    List<Session> findCompletedSessionsByTeacherStudentAndDateRange(@Param("teacherId") Long teacherId,
                                                                  @Param("studentId") Long studentId,
                                                                  @Param("startDate") LocalDateTime startDate, 
                                                                  @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT SUM(s.totalEarnings) FROM Session s WHERE s.teacher.id = :teacherId AND s.sessionDateTime >= :startDate AND s.sessionDateTime <= :endDate AND s.isCompleted = true")
    BigDecimal getTotalEarningsByTeacherAndDateRange(@Param("teacherId") Long teacherId, 
                                                   @Param("startDate") LocalDateTime startDate, 
                                                   @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT SUM(s.totalEarnings) FROM Session s WHERE s.teacher.id = :teacherId AND s.student.id = :studentId AND s.sessionDateTime >= :startDate AND s.sessionDateTime <= :endDate AND s.isCompleted = true")
    BigDecimal getTotalEarningsByTeacherStudentAndDateRange(@Param("teacherId") Long teacherId,
                                                          @Param("studentId") Long studentId,
                                                          @Param("startDate") LocalDateTime startDate, 
                                                          @Param("endDate") LocalDateTime endDate);
}
