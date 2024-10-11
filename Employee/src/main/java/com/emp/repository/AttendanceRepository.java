package com.emp.repository;

import com.emp.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {


    List<Attendance> findAllByEmployee_Id(Long employeeId);
}


