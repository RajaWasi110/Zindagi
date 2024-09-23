package com.emp.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "employee", schema = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotNull(message = "Age cannot be null")
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 65, message = "Age must be less than or equal to 65")
    private int age;

    @NotNull(message = "Position cannot be null")
    @Size(min = 2, max = 30, message = "Position must be between 2 and 30 characters")
    private String position;

    @NotNull(message = "CNIC cannot be null")
    @Pattern(regexp = "\\d{13}", message = "CNIC must be a 13-digit number")
    private String cnic;

    @NotNull(message = "Address cannot be null")
    @Size(min = 5, max = 100, message = "Address must be between 5 and 100 characters")
    private String address;

    @NotNull(message = "Date of Birth cannot be null")
    @Past(message = "Date of Birth must be in the past")
    private LocalDate dob;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    // Constructors
    public Employee() {}

    public Employee(String name, int age, String position, String cnic, String address, LocalDate dob, String email) {
        this.name = name;
        this.age = age;
        this.position = position;
        this.cnic = cnic;
        this.address = address;
        this.dob = dob;
        this.email = email;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
