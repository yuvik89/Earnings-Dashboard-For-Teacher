package com.earnings.dashboard.repository;

import com.earnings.dashboard.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByTeacherId(Long teacherId);
    boolean existsByRegistrationId(String registrationId);
}
