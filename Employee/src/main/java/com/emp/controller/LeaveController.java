package com.emp.controller;

import com.emp.model.Employee;
import com.emp.model.LeaveRequest;
import com.emp.repository.EmployeeRepository;
import com.emp.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/leaves")
public class LeaveController {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employee/{employeeId}")
    public List<LeaveRequest> getLeavesByEmployee(@PathVariable Long employeeId) {
        return leaveRepository.findAllByEmployee_Id(employeeId);
    }

    @PostMapping("/request")
    public ResponseEntity<String> requestLeave(@RequestParam int employeeId,
                                               @RequestParam String reason,
                                               @RequestParam String startDate,
                                               @RequestParam String endDate) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployee(employee);
        leaveRequest.setStartDate(LocalDate.parse(startDate));
        leaveRequest.setEndDate(LocalDate.parse(endDate));
        leaveRequest.setReason(reason);
        leaveRepository.save(leaveRequest);

        return ResponseEntity.ok("Leave request submitted successfully");
    }
}
