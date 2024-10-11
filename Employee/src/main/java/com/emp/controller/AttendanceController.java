package com.emp.controller;

import com.emp.model.Attendance;
import com.emp.model.Employee;
import com.emp.repository.AttendanceRepository;
import com.emp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employee/{employeeId}")
    public List<Attendance> getAttendanceByEmployee(@PathVariable Long employeeId) {
        return attendanceRepository.findAllByEmployee_Id(employeeId);
    }

    @PostMapping("/mark")
    public ResponseEntity<String> markAttendance(@RequestParam int employeeId, @RequestParam boolean present) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setDate(LocalDate.now());
        attendance.setPresent(present);
        attendanceRepository.save(attendance);
        return ResponseEntity.ok("Attendance marked successfully");
    }
}
