package com.emp.repository;

import com.emp.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LeaveRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findAllByEmployee_Id(Long employeeId);
}
