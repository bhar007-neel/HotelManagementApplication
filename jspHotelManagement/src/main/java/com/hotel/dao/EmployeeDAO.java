package com.hotel.dao;

import com.hotel.model.Employee;
import com.hotel.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    // Retrieve all employees
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM \"Employee\"";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Employee emp = new Employee(
                        rs.getInt("EmployeeID"),
                        rs.getString("Name"),
                        rs.getString("Address"),
                        rs.getString("SSN"),
                        rs.getString("EmploymentType") // Maps to role in the model
                );
                employees.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    // Retrieve employee by ID
    public Employee getEmployeeById(int employeeId) {
        Employee employee = null;
        String query = "SELECT * FROM \"Employee\" WHERE \"EmployeeID\" = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                employee = new Employee(
                        rs.getInt("EmployeeID"),
                        rs.getString("Name"),
                        rs.getString("Address"),
                        rs.getString("SSN"),
                        rs.getString("EmploymentType")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    // Add a new employee
    public void addEmployee(Employee emp, int hotelId) {
        String query = "INSERT INTO \"Employee\" (\"HotelID\", \"Name\", \"Address\", \"SSN\", \"EmploymentType\") VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, hotelId);
            stmt.setString(2, emp.getName());
            stmt.setString(3, emp.getAddress());
            stmt.setString(4, emp.getSsn());
            stmt.setString(5, emp.getRole());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update an existing employee
    public void updateEmployee(Employee emp) {
        String query = "UPDATE \"Employee\" SET \"Name\"=?, \"Address\"=?, \"SSN\"=?, \"EmploymentType\"=? WHERE \"EmployeeID\"=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, emp.getName());
            stmt.setString(2, emp.getAddress());
            stmt.setString(3, emp.getSsn());
            stmt.setString(4, emp.getRole());
            stmt.setInt(5, emp.getEmployeeId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete an employee
    public void deleteEmployee(int employeeId) {
        String query = "DELETE FROM \"Employee\" WHERE \"EmployeeID\" = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, employeeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
