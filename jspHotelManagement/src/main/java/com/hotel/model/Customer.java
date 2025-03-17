package com.hotel.model;

public class Customer {
    private int customerId;
    private String name;
    private String address;
    private String idType; // "SSN", "SIN", or "Driver's License"
    private String idNumber; // Must conform to length constraints
    private String registrationDate; // Format: YYYYMMDD

    // Constructors
    public Customer() {}

    public Customer(int customerId, String name, String address, String idType, String idNumber, String registrationDate) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.idType = idType;
        this.idNumber = idNumber;
        this.registrationDate = registrationDate;
    }

    // Getters and Setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        if (idType.equals("SSN") || idType.equals("SIN") || idType.equals("Driver's License")) {
            this.idType = idType;
        } else {
            throw new IllegalArgumentException("Invalid ID Type. Must be 'SSN', 'SIN', or 'Driver's License'.");
        }
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        if ((this.idType.equals("SSN") || this.idType.equals("SIN")) && idNumber.length() == 9) {
            this.idNumber = idNumber;
        } else if (this.idType.equals("Driver's License") && idNumber.length() <= 19) {
            this.idNumber = idNumber;
        } else {
            throw new IllegalArgumentException("Invalid ID Number length for the specified ID Type.");
        }
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        if (registrationDate.matches("\\d{8}")) {
            this.registrationDate = registrationDate;
        } else {
            throw new IllegalArgumentException("Registration Date must be in YYYYMMDD format.");
        }
    }
}
