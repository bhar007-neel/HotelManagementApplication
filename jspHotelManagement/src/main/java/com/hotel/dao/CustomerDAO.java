package com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hotel.model.Customer;
import com.hotel.utils.DBConnection;

public class CustomerDAO {

    public int addCustomer(String name, String address, String idType, String idNumber, String registrationDate) {
        int customerId = -1;
    
        String sql = "INSERT INTO \"Customer\" (\"Name\", \"Address\", \"IDType\", \"IDNumber\", \"RegistrationDate\") " +
                     "VALUES (?, ?, ?, ?, ?) RETURNING \"CustomerID\"";
    
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setString(3, idType);
            stmt.setString(4, idNumber);
            stmt.setString(5, registrationDate);
    
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                customerId = rs.getInt("CustomerID");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return customerId;
    }
    
    

    public void deleteCustomer(int customerId) {
        String deleteSql = "DELETE FROM \"Customer\" WHERE \"CustomerID\" = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {

            preparedStatement.setInt(1, customerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCustomer(int customerId, String name, String address) {
        String updateSql = "UPDATE \"Customer\" SET \"Name\" = ?, \"Address\" = ? WHERE \"CustomerID\" = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, address);
            preparedStatement.setInt(3, customerId);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Customer updated successfully.");
            } else {
                System.out.println("No rows updated.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM \"Customer\"";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                customers.add(new Customer(
                        rs.getInt("CustomerID"),
                        rs.getString("Name"),
                        rs.getString("Address"),
                        rs.getString("IDType"),
                        rs.getString("IDNumber"),
                        rs.getString("RegistrationDate")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public Integer findCustomerId(String idType, String idNumber) {
        String sql = "SELECT \"CustomerID\" FROM \"Customer\" WHERE \"IDType\" = ? AND \"IDNumber\" = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, idType);
            stmt.setString(2, idNumber);
    
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("CustomerID");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
