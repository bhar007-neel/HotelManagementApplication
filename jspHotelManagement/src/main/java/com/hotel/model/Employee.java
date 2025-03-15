package com.hotel.model;

public class Employee {
    private int employeeId;
    private String name;
    private String address;
    private String ssn; // Social Security Number or SIN
    private String role; // Role in the hotel

    // Constructors
    public Employee() {}

    public Employee(int employeeId, String name, String address, String ssn, String role) {
        this.employeeId = employeeId;
        this.name = name;
        this.address = address;
        this.ssn = ssn;
        this.role = role;
    }

    // Getters and Setters
    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public String getName() { return name; }
    public void setName(String fullName) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getSsn() { return ssn; }
    public void setSsn(String ssn) { this.ssn = ssn; }

    public String getRole() { return role; }
    public void setRole(String position) { this.role = role; }
}

