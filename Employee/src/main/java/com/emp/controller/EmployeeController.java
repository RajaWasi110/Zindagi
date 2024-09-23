package com.emp.controller;


import com.emp.exception.ExpiredTokenException;
import com.emp.exception.InvalidTokenException;
import com.emp.exception.MissingTokenException;
import com.emp.exception.TokenException;
import com.emp.model.Employee;
import com.emp.response.ApiResponse;
import com.emp.service.EmployeeService;
import com.emp.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmployeeService employeeService;



    @GetMapping("/getToken")
    @SecurityRequirement(name = "basicAuth")
    public String getToken(@RequestParam String username, @RequestParam String password) {
        if ("user".equals(username) && "password".equals(password)) {
            return jwtUtil.generateToken(username);
        }
        throw new TokenException("Invalid credentials!");
    }
    @PostMapping("/add")
    public ApiResponse<Employee> add( @RequestHeader("Authorization") String token, @Valid @RequestBody Employee employee) {
        validateToken(token);
        Employee savedEmployee = employeeService.addEmployee(employee);
        return new ApiResponse("00", "200", savedEmployee, "Employee added successfully.");
    }

    @GetMapping("/get/{id}")
    public ApiResponse getEmployeeById(@RequestHeader("Authorization") String token, @PathVariable int id) {
        validateToken(token);
        Employee employee = employeeService.getEmployeeById(id);
        return new ApiResponse("00", "200", employee, "Employee found.");
    }

    @GetMapping("/getAll")
    public ApiResponse getAllEmployees(@RequestHeader("Authorization") String token) {
        validateToken(token);
        List<Employee> employees = employeeService.getAllEmployees();
        return new ApiResponse("00", "200", employees, "List of all employees.");
    }

    @PutMapping("/update/{id}")
    public ApiResponse updateEmployee(@RequestHeader("Authorization") String token, @PathVariable int id, @RequestBody Employee updatedEmployee) {
        validateToken(token);
        Employee updatedEmp = employeeService.updateEmployee(id, updatedEmployee);
        return new ApiResponse("00", "200", updatedEmp, "Employee updated successfully.");
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse deleteEmployee(@RequestHeader("Authorization") String token, @PathVariable int id) {
        validateToken(token);
        Employee employee = employeeService.getEmployeeById(id);
        employeeService.deleteEmployee(id);
        return new ApiResponse("00", "200", employee, "Employee with "+id+ " is deleted successfully.");
    }



    private void validateToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new MissingTokenException("Token is missing");
        }
        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        try {
            jwtUtil.validateToken(jwtToken);
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException("Token has expired");
        } catch (JwtException e) {
            throw new InvalidTokenException("Token is invalid");
        }
    }
}
